#!/bin/sh
sudo -v
cd /vagrant
mvn verify &&
sudo cp /vagrant/target/tyro.war /opt/tyro/app/app.war