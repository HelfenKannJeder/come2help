#!/bin/bash

if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
	echo "We won't deploy because we're on a pull request."
	exit 0
fi

if [ $TRAVIS_BRANCH != "master" ]; then
    echo "We won't deploy because we are not on the master branch."
    exit 0
fi

# If GH_TOKEN is not set, we'll exit gracefully
if [ -z ${GH_TOKEN:+1} ]; then
	echo "The GH_TOKEN ENV is not set. Thus, we'll won't deploy to gh-pages."
	exit 0
fi

set -e # exit with nonzero exit code if anything fails

# Create an empty deploy directory. Any data that's going to be deployed shall be in there.
mkdir -p deploy

# Create the JSONdoc zip
mvn package -Pdistributable -Dmaven.test.skip=true -Dfindbugs.skip=true

echo
echo

# Copy the jsondoc zip to the deply directory
cp target/jsondoc-distribution.zip deploy

# go to the deploy directory and create a *new* Git repo
cd deploy
git init

# inside this git repo we'll pretend to be a new user
git config user.name "Travis CI"
git config user.email "travis@come2.help"

# The first and only commit to this new Git repo contains all the
# files present with the commit message "Deploy to GitHub Pages".
git add .
git commit -m "Deploy to GitHub Pages"

# Force push from the current repo's master branch to the remote
# repo's gh-pages branch. (All previous history on the gh-pages branch
# will be lost, since we are overwriting it.) We redirect any output to
# /dev/null to hide any sensitive credential data that might otherwise be exposed.
git push --force --quiet "https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git" master:gh-pages > /dev/null 2>&1

# Restore PWD
cd $OLDPWD