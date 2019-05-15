import urllib.request
import json

username = input("Username: ")
paycycle = input("Paycycle: ")


url = 'https://work-hours-management-app.herokuapp.com/paycycles/'+username+'/'+paycycle
# url = 'http://localhost:8080/paycycles/user1/Tammikuu'
print(url)
req = urllib.request.Request(url)

##parsing response
r = urllib.request.urlopen(req).read()
cont = json.loads(r.decode('utf-8'))
counter = 0

print("---------------------")
##parcing json
for item in cont[0]['workdays']:
    counter += 1
    print("Date:", item['date'], "\nWorkhours:", item['workhours'], "\nStartingtime:", item['startingtime'], "\nEndingtime:", item['endingtime'])
    print("---------------------")

##print formated
#print (json.dumps(cont, indent=4, sort_keys=True))
print("Number of titles: ", counter)
