(ns textuality.core)

(import 'org.jsoup.Jsoup)

(defn text-from
  [html]
  (.text (.body (Jsoup/parse html))))