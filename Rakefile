require 'rubygems'
require 'rake'

JAR='lib/javascreepy/javascreepy.jar'

java = RUBY_PLATFORM =~ /java/

begin
  require 'jeweler'
  Jeweler::Tasks.new do |gem|
    gem.name = "javascreepy"
    gem.summary = "Wrapper for JSR-223"
    gem.description = "Wrapper for JSR-223"
    gem.email = "serabe@gmail.com"
    gem.homepage = "http://github.com/Serabe/javascreepy"
    gem.authors = ["Sergio Arbeo"]
    gem.add_development_dependency "rspec", ">= 1.2.9"
    gem.add_development_dependency "cucumber", ">= 0"
    # gem is a Gem::Specification... see http://www.rubygems.org/read/chapter/20 for additional settings
    gem.platform = 'java'
    gem.files += [JAR]
  end
  Jeweler::GemcutterTasks.new
rescue LoadError
  puts "Jeweler (or a dependency) not available. Install it with: sudo gem install jeweler"
end

if java
  require 'spec/rake/spectask'
  Spec::Rake::SpecTask.new(:spec => :build_jar) do |spec|
    spec.libs << 'lib' << 'spec'
    spec.spec_files = FileList['spec/**/*_spec.rb']
  end

  Spec::Rake::SpecTask.new(:rcov) do |spec|
    spec.libs << 'lib' << 'spec'
    spec.pattern = 'spec/**/*_spec.rb'
    spec.rcov = true
  end
else
  task :spec => :build_jar do
    cp = File.join('.', 'lib', 'jruby.jar')
    system "java -cp #{cp} org.jruby.Main -S rake spec"
  end
end

task :spec => :check_dependencies

begin
  require 'cucumber/rake/task'
  Cucumber::Rake::Task.new(:features)

  task :features => :check_dependencies
rescue LoadError
  task :features do
    abort "Cucumber is not available. In order to run features, you must: sudo gem install cucumber"
  end
end

task :default => :spec

require 'rake/rdoctask'
Rake::RDocTask.new do |rdoc|
  version = File.exist?('VERSION') ? File.read('VERSION') : ""

  rdoc.rdoc_dir = 'rdoc'
  rdoc.title = "javascreepy #{version}"
  rdoc.rdoc_files.include('README*')
  rdoc.rdoc_files.include('lib/**/*.rb')
end

desc "Removes all generated class files."
task :clean_classes do
  rm_globs 'ext/java/javascreepy/*.class'
end

desc "Remove the generated jar."
task :clean_jar do
  rm_globs JAR
end

desc "Clean both classes and generated jar"
task :clean_all => ['java:clean_classes', 'java:clean_jar']

desc "Build external library"
task :build_external do
  Dir.chdir('ext/java') do
    CLASS_PATH="../../lib/jruby.jar"
    sh "javac -cp #{CLASS_PATH} javascreepy/*.java"
    sh "jar cf ../../#{JAR} javascreepy/*.class"
  end
end

task :build_jar => ["clean_jar", "build_external", "clean_classes"]

def rm_globs(*globs)
  globs.each{|glob| FileUtils.rm Dir.glob(glob)}
end
