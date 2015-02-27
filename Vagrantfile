# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = '2'
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
    config.vm.define 'postgresql' do |postgresql|
        postgresql.vm.box = 'chef/centos-7.0'
        postgresql.vm.network "private_network", ip: "10.17.71.10"
        postgresql.vm.network "forwarded_port", guest: 5432, host: 5432
    end

    config.vm.define 'tomcat', primary: true do |tomcat|
        tomcat.vm.box = 'chef/centos-7.0'
        tomcat.vm.network "private_network", ip: "10.17.71.20"
        tomcat.vm.network "forwarded_port", guest: 80, host: 8080
        tomcat.vm.network "forwarded_port", guest: 443, host: 8443
        tomcat.vm.network "forwarded_port", guest: 9876, host: 9876
        tomcat.vm.network "forwarded_port", guest: 8888, host: 8888
    end

    config.vm.provision 'ansible', run: 'always' do |ansible|
        ansible.playbook = 'ansible/site.yml'
        ansible.groups = {
            'postgresql' => ['postgresql'],
            'tomcat' => ['tomcat']
        }
        ansible.extra_vars = {
            environment: "development",
            postgresql: {
                address: "10.17.71.10",
                port: "5432",
                database: "tyro",
                user: "tyro",
                password: "tyro"
            }
        }
    end
end
