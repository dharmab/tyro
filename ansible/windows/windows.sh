#!/bin/sh
which ansible-playbook
if [ $? -ne 0 ]; then
    echo "Installing Ansible..."
    yum -y install http://mirror.metrocast.net/fedora/epel/7/x86_64/e/epel-release-7-5.noarch.rpm
    yum -y install ansible
fi
echo "Running Ansible provisioner. This may take a few minutes..."
ansible-playbook -c local -i /vagrant/ansible/windows/inventory /vagrant/ansible/site.yml