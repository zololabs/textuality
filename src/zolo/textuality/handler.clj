(ns zolo.textuality.handler
  (:use compojure.core)
  (:require [zolo.textuality.core :as textuality]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Textuality web service")
  (GET "/parse"  {{html :html} :params}
       (textuality/text-from html))
  (POST "/parse" {{html :html} :params}
        (textuality/text-from html))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
