# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = '2'
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
    config.vm.define 'database' do |database|
        database.vm.box = 'chef/centos-7.0'
        database.vm.network "private_network", ip: "10.17.71.10"
        database.vm.network "forwarded_port", guest: 5432, host: 5432
    end

    config.vm.define 'development', primary: true do |development|
        development.vm.box = 'chef/centos-7.0'
        development.vm.network "private_network", ip: "10.17.71.20"
        development.vm.network "forwarded_port", guest: 80, host: 8000
    end

    config.vm.define 'production', autostart: false do |production|
        production.vm.box = 'chef/centos-7.0'
        production.vm.network "private_network", ip: "10.17.71.30"
        production.vm.network "forwarded_port", guest: 80, host: 8080
        production.vm.network "forwarded_port", guest: 443, host: 8443
    end

    config.vm.provision 'ansible', run: 'always' do |ansible|
        ansible.playbook = 'ansible/site.yml'
        ansible.groups = {
            'database' => ['database'],
            'development' => ['development'],
            'production' => ['production']
        }
        ansible.extra_vars = {
            database: {
                address: "10.17.71.10",
                port: "5432",
                database: "jazz",
                user: "jazz",
                password: "jazz"
            }
        }
    end
end
