
RULES_FILE = "rulescore.txt"

tableStart = """\\begin{table}[h!]
\\centering
\\begin{tabular}{lcr}
\\toprule
Pattern & Count \\\\ 
\\midrule
"""

tableEnd = """\\bottomrule
\\end{tabular}
\\caption{Design Patterns reported by Pattern4J.}
\\label{tab:patterns}
\\end{table}"""


counts = {}

with open(RULES_FILE, "r") as inFile:
    for line in inFile:
        parts = line.split("\t")
        key = parts[1][:-1]
        if key not in counts:
            counts[key] = 0
        counts[key] += 1

output_name = RULES_FILE.split(".")[0] + "_table.txt"

total = 0
keys = 0

arr = []

sorted_keys = sorted(counts, key=lambda k: counts[k], reverse=True)

with open(output_name, "w") as outFile:
    outFile.write(tableStart)
    for key in sorted_keys:
        outFile.write(key + " & " + str(counts[key]) + " \\\\\n")
        total += counts[key]
        keys += 1
    outFile.write(tableEnd)

print("Total flaws found: " + str(total))
print("Total keys found: " + str(keys))
