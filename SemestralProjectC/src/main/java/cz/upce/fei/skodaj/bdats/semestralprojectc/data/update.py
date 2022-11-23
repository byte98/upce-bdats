# Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
# 
# This file is part of second semestral project for data structures subject.
# 
# Second semestral project for data structures subject is free software:
# you can redistribute it and/or modify it under the terms of the GNU General
# Public License as published by the Free Software Foundation, either version 2
# of the License, or (at your option) any later version.
# 
# This file is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this file.  If not, see <http://www.gnu.org/licenses/>.

from codecs import strict_errors
from io import TextIOWrapper
from itertools import count
from math import nan
from sys import float_info
from turtle import down
from unittest import result
import urllib
import requests
from bs4 import BeautifulSoup

# URL to page with all castles
URL = "https://cs.wikipedia.org/wiki/Seznam_z%C3%A1mk%C5%AF_v_%C4%8Cesku"

# Default wiki page
WIKI = "https://cs.wikipedia.org"

# List of links which will not be processed
FORBIDDEN = [
    "https://cs.wikipedia.org/wiki/International_Standard_Book_Number",
    "https://cs.wikipedia.org/wiki/Speci%C3%A1ln%C3%AD:Zdroje_knih/80-85-983-61-3",
    "https://cs.wikipedia.org/wiki/Seznam_zanikl%C3%BDch_z%C3%A1mk%C5%AF_v_%C4%8Cesku",
    "https://cs.wikipedia.org/wiki/Seznam_loveck%C3%BDch_hr%C3%A1dk%C5%AF_a_z%C3%A1me%C4%8Dk%C5%AF_v_%C4%8Cesku"
]

# Name of output file
OUTPUT = "../../../../../../../../resources/dataset.csv"

def download(url: str) -> str:
    '''
    Downloads page from internet
    :param url: URL to page which will be downloaded
    :returns: Content of downloaded page
    '''
    reti: str = ""
    lines = []
    file = urllib.request.urlopen(url)
    reti = file.read()
    return reti

def get_list(url: str) -> list:
    '''
    Gets list of hyperlinks to wikipedia pages with all castles
    :param url: URL to page with list of all castles
    :returns: List of links to wiki pages with castles
    '''
    reti: list = []
    soup = BeautifulSoup(download(url), "html.parser")
    content = soup.find(id="mw-content-text")
    lists = content.find_all("ul")
    for list in lists:
        links = list.find_all("a")
        for link in links:
            if link.has_attr("href") and link["href"].startswith("/wiki") and WIKI + link["href"] not in FORBIDDEN and not (link.has_attr("class") and link["class"] == "new"):
                reti.append(WIKI + link["href"])
                print("Found " + WIKI + link["href"])
    
    return reti

def remove_diacritics(input: str) -> str:
    '''
    Removes diacritics from string
    :param input: String from which diacritics will be removed
    :returns: String without diacritics
    '''
    diacritics  = "ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝßàáâãäåçèéêëìíîïñòóôõöùúûüýÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſ"
    replacement = "AAAAAACEEEEIIIINOOOOOUUUUYsaaaaaaceeeeiiiinooooouuuuyyAaAaAaCcCcCcCcDdDdEeEeEeEeEeGgGgGgGgHhHhIiIiIiIiIiKkkLlLlLlLlLlNnNnNnNnNOoOoOoRrRrRrSsSsSsSsTtTtTtUuUuUuUuUuUuWwYyYZzZzZzs"
    reti = input
    for i in range(0, len(diacritics)):
        if diacritics[i] in reti:
            reti = reti.replace(diacritics[i], replacement[i])
    return reti

def get_data(url: str) -> tuple:
    '''
    Gets information about castle from its wiki page
    :param URL: URL to wiki page of castle
    :returns: Tuple containing name[0] of castle, latitude[1] and longitude[2] of castle
    '''
    soup = BeautifulSoup(download(url), "html.parser")
    
    # Follows finding name of castle
    table = soup.find("table", {"class": "infobox"})
    th = table.find("th")
    name = th.text
    if name.startswith("Zámek ") and not (name.startswith("Zámek v ") or name.startswith("Zámek ve ") or name.startswith("Zámek a")):
        name = name.replace("Zámek ", "")
    name = remove_diacritics(name)
    

    # Next find location of castle
    divcoords = soup.find(id="mw-indicator-coordinates")
    link = divcoords.find("a")
    parts = link.text.split(" s. š., ")
    lat = get_coord(parts[0])
    lon = get_coord(parts[1].replace(" v. d.", ""))
    return (name, lat, lon)

def get_coord(val: str) -> float:
    '''
    Gets coordinate from its textual representation
    :param val: Textual representation of coordinate
    :returns: Parsed coordinate value
    '''
    reti: float = nan
    degparts = val.split("°")
    deg = float(degparts[0])
    minparts = degparts[1].split("′")
    min = float(minparts[0])
    secpart = minparts[1].replace(",", ".").replace("″", "")
    sec = float(secpart)
    reti = deg + (min / 60) + (sec / 3600)
    return reti

def main():
    '''
    Main function of program
    '''
    results = []
    print("Getting list of castles...")
    links = get_list(URL)
    counter = 1
    print("Getting more information about castles...")
    for link in links:
        print("[" + str(counter) + " / " + str(len(links)) + " (" + "{:.2f}".format((counter / len(links)) * 100) + " %)]: " + link, flush=True)
        results.append(get_data(link))
        counter += 1
    print("Writing results to file...")
    with open(OUTPUT, "w", encoding="utf-8") as file:
        for result in results:
            file.write(result[0] + "," + str(result[1]) + "," + str(result[2]) + "\n")
    print("FINISHED")
if __name__ == "__main__":
    main()
