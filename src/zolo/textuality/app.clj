(ns zolo.textuality.app
  (:use zolo.utils.debug
        compojure.core
        ring.adapter.jetty)
  (:require [zolo.textuality.parser :as parser]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.tools.cli :as cli]))

(defn status [request-params]
  {:status 200
   :body {:working true}})

(defroutes app-routes
  (GET "/" [] "Textuality web service")
  (GET "/server/status" {params :params} (status params))
  (GET "/parse" {{html :html} :params}
       (if (nil? html)
         {:status 400}
         (clojure.string/replace (parser/parse html) #"\n" "<br/>")))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn start-textuality
  ([]
     (start-textuality 8000))
  ([port]
     (run-jetty (var app) {:port port :join? false})))

(defn process-args [args]
  (cli/cli args
           ["-p" "--port" "Listen on this port" :default 8000  :parse-fn #(Integer. %)] 
           ["-h" "--help" "Show help" :default false :flag true]))

(defn -main [& cl-args]
  (print-vals "CL Args :" cl-args)
  (let [[options args banner] (process-args cl-args)]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (start-textuality (:port options))))