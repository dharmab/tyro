#!/bin/sh
# Run Xvfb without access control on display :2
export DISPLAY=:2
# Start Xvfb if it is not already started.
ps -ef | grep Xvfb | grep -v grep >> /dev/null || Xvfb :2 -ac &
# Run GWT dev mode via Maven
cd /vagrant
mvn gwt:run