(ns zolo.textuality.handler
  (:use compojure.core)
  (:require [zolo.textuality.core :as textuality]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Textuality web service")
  (POST "/parse"
        {{html :html} :params}
        (if (nil? html) {:status 400}  (textuality/text-from html)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
