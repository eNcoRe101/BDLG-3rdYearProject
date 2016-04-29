import sys

dimentions = int(sys.argv[1]) + 1
fileOut = file("sampledata-" + str(dimentions-1) + ".csv", "w")
print dimentions
for i in range(0, dimentions):
  if i > 0:
    fileOut.write("N" + str(i) + ",")
  else:
    fileOut.write(",")

  for j in range(1, dimentions):
    if i == 0:
      fileOut.write("E" + str(j))
      if(j != dimentions-1):
        fileOut.write(",")
    elif i > 0 and j < dimentions-1:
      fileOut.write("1,")
    else:
      fileOut.write("1")
  
  fileOut.write("\n")

