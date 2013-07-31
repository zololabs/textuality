(ns zolo.textuality.core
  (:require [clojure.zip :as zip]
            [clojure.xml :as xml]
            [clojure.walk :as walk]))

(import 'org.jsoup.Jsoup)

(defn text-from
  ([html]
     (.text (.body (Jsoup/parse html)))))

(defn zip-str [s]
  (zip/xml-zip (xml/parse (java.io.ByteArrayInputStream. (.getBytes s)))))

(defn convert-html
  [html]
  (walk/postwalk #(do (println "visiting: " %) %) (zip-str html)))

