# -*- mode: ruby -*-
# vi: set ft=ruby :

def which(cmd)
    exts = ENV['PATHEXT'] ? ENV['PATHEXT'].split(';') : ['']
    ENV['PATH'].split(File::PATH_SEPARATOR).each do |path|
        exts.each { |ext|
            exe = File.join(path, "#{cmd}#{ext}")
            return exe if File.executable? exe
        }
    end
    return nil
end

VAGRANTFILE_API_VERSION = '2'
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
    config.vm.define 'development' do |development|
        development.vm.box = 'chef/centos-7.0'
        development.vm.synced_folder ".", "/vagrant", mount_options: ['dmode=775', 'fmode=664']
        development.vm.network "forwarded_port", guest: 80, host: 8080
        development.vm.network "forwarded_port", guest: 443, host: 8443
        development.vm.network "forwarded_port", guest: 5432, host: 5432
    end

    if which('ansible-playbook')
        config.vm.provision 'ansible', run: 'always' do |ansible|
            ansible.playbook = 'ansible/site.yml'
            ansible.groups = {
                'tomcat' => ['development'],
                'postgresql' => ['development']
            }
        end
    else
        config.vm.provision :shell, run: 'always', path: "ansible/windows/windows.sh", keep_color: true
    end

end
