#!/bin/sh

git add .

echo "Enter Commit Name for Changes: "

read commitName

git commit -m "$commitName"

git push

echo "Data Pushed TO Git ($commitName) Successfully."
