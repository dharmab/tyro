#!/bin/sh
cd /vagrant
sudo -v
mvn verify &&
sudo cp /vagrant/target/tyro.war /opt/tyro/app/app.war