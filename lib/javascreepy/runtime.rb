module Javascreepy
  class Runtime

    attr_reader :lang
    attr_accessor :events

    def initialize(lang)
      @lang = "javascript"
      @events = Events.new
    end
  end
end
