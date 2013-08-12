(ns zolo.textuality.handler
  (:use compojure.core)
  (:require [zolo.textuality.parser :as textuality]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Textuality web service")
  (POST "/parse"
        {{html :html} :params}
        (if (nil? html) {:status 400}  (textuality/parse html)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
