(defproject textuality "0.1.0-SNAPSHOT"
  :description ""
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.jsoup/jsoup "1.7.2" ]
                 [compojure "1.1.5"]
                 [ring-mock "0.1.5"]
                 [ring-serve "0.1.2"]
                 [commons-codec/commons-codec "1.4"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler zolo.textuality.handler/app}
  :repl-options {:init (do
                         (use 'ring.util.serve) 
                         (use 'clojure.pprint)
                         (use 'clojure.test)
                         (use 'zolo.textuality.parser)
                         )}
  )
