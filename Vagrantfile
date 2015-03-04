# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = '2'
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
    config.vm.define 'development' do |development|
        development.vm.box = 'chef/centos-7.0'
        development.vm.network "forwarded_port", guest: 80, host: 8080
        development.vm.network "forwarded_port", guest: 443, host: 8443
        development.vm.network "forwarded_port", guest: 5432, host: 5432
    end

    config.vm.provision 'ansible', run: 'always' do |ansible|
        ansible.playbook = 'ansible/site.yml'
        ansible.groups = {
            'tomcat' => ['development'],
            'postgresql' => ['development']
        }
        ansible.extra_vars = {
            postgresql: {
                address: "localhost",
                port: "5432",
                database: "tyro",
                user: "tyro",
                password: "tyro"
            }
        }
    end
end
