#! /bin/bash

# Counts the number of classes in which at least one design pattern is used.

grep -o 'element="[^"]*"' patterns.xml \
| sed 's/.*element="\([^":$]*\).*/\1/' \
| sort -u \
| wc -l
