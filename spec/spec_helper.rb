$LOAD_PATH.unshift(File.dirname(__FILE__))
$LOAD_PATH.unshift(File.join(File.dirname(__FILE__), '..', 'lib'))
require 'javascreepy'
require 'spec'
require 'spec/autorun'

Spec::Runner.configure do |config|
  
end

class SpecEvents
  
  attr_reader :value
  
  def write string
    @value = string
  end
end