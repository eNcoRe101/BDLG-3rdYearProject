
ajmatrix = [[1,0,1,1],
         [1,1,0,0],
         [0,1,1,0],
         [0,1,1,1],
         [1,0,0,1],
         [0,1,0,0]
        ]

exspearnceScore = []
learingScore = []

def GenrateSkill(matrix):
  kMatrix = matrix
  people = [ 0 for i in range(len(matrix[0]))]

  for i, row in enumerate(kMatrix):
    currentEx = 0
    for j, event in enumerate(row):
      
      if event:
        people[j] += 1
        currentEx += 1
        kMatrix[i][j] = currentEx

  for i, row in enumerate(kMatrix):
    for j, event in enumerate(row):
      if event:
        kMatrix[i][j] += 0.5 * (people[j] - 1)


  print people
  return kMatrix


print (GenrateSkill(ajmatrix))

