openF = open("D:/SpringWorks/ImageGANMaker/src/test/java/com/teadone/IGM/encode.txt", "r", encoding='utf-8')
readS = openF.read()
readL = list(readS)
newL = []
for i in readL:
    ordn = ord(i) + 100
    i = chr(ordn)
    newL.append(i)

saveS = "".join(newL)
saveF = open("D:/SpringWorks/ImageGANMaker/src/test/java/com/teadone/IGM/encode.txt", "w", encoding='utf-8')
saveF.write(saveS)

print(saveS)
openF.close()
saveF.close()