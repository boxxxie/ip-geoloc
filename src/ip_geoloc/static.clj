(ns ip-geoloc.static
  (:require
   [ip-geoloc.maxmind :as mmind]
   [ip-geoloc.util :as util]
   ))

(defn init-context [config]
  (mmind/start-maxmind config))


(defn full-geo-lookup [provider ip]
  (mmind/full-geo-lookup provider (util/ip-conversion ip)))


(defn coordinates [provider ip]
  (mmind/coordinates provider (util/ip-conversion ip)))


(defn geo-lookup [provider ip]
  (mmind/geo-lookup provider (util/ip-conversion ip)))
