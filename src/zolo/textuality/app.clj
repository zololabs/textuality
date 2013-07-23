(ns zolo.textuality.app
  (:use compojure.core)
  (:require [compojure-route :as route]))

(defroutes app
  (GET "/parse" [html :as {params :params}]))