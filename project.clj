(defproject textuality "0.1.0-SNAPSHOT"
  :description "Textuality"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [zololabs/zolo-utils "0.1.0-SNAPSHOT"]

                 [compojure "1.1.5"]
                 [org.clojure/tools.cli "0.2.2"]

                 [ring-mock "0.1.5"]
                 [ring-serve "0.1.2"]

                 [ch.qos.logback/logback-classic "1.0.7"]
                 [commons-codec/commons-codec "1.4"]
                 [org.jsoup/jsoup "1.7.2" ]]

  :plugins [[lein-ring "0.8.5"]]
  :repl-options {:init (do
                         (use 'ring.util.serve) 
                         (use 'clojure.pprint)
                         (use 'clojure.test)
                         (use 'zolo.textuality.parser)
                         )}
  :main zolo.textuality.app
)
