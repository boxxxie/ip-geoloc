(ns ip-geoloc.core
  (:require [ip-geoloc.maxmind :as mmind]))


(def ^:dynamic *provider* nil)


(defn start-provider
  ([]
   (start-provider {}))
  ([config]
   (mmind/start-maxmind config)))


(defn stop-provider [provider]
  (mmind/stop-maxmind provider))


(defn start-provider!
  ([]
   (start-provider! {}))
  ([config]
   (alter-var-root #'*provider*
                   (constantly (start-provider config)))))


(defn stop-provider! []
  (alter-var-root #'*provider*
                  (constantly (stop-provider *provider*))))

;; thanks chatgpt
(defn ip-to-int [ip]
  (let [parts (map #(Integer/parseInt %) (.split ip "\\."))]
    (reduce (fn [acc part] (+ (* acc 256) part)) 0 parts)))

;; thanks chatgpt
(defn int-to-ip [int-ip]
  (format "%d.%d.%d.%d"
          (bit-shift-right int-ip 24)
          (bit-shift-right (bit-and int-ip 0x00FF0000) 16)
          (bit-shift-right (bit-and int-ip 0x0000FF00) 8)
          (bit-and int-ip 0x000000FF)))

(defn- ip-conversion [ip]
  (cond
    (string? ip) ip
    (int? ip)    (int-to-ip ip)))

(defn full-geo-lookup
  ([ip]
   (full-geo-lookup *provider* ip))
  ([{:keys [provider]} ip]
   (mmind/full-geo-lookup @provider (ip-conversion ip))))


(defn coordinates
  ([ip]
   (coordinates *provider* ip))
  ([{:keys [provider]} ip]
   (mmind/coordinates @provider (ip-conversion ip))))


(defn geo-lookup
  ([ip]
   (geo-lookup *provider* ip))
  ([{:keys [provider]} ip]
   (mmind/geo-lookup @provider (ip-conversion ip))))


(comment

  (def ip1 "220.181.108.182")
  (def ip2 "104.131.115.133")
  (def ip3 "23.232.137.112")

  (def prvd (start-provider {}))

  (geo-lookup prvd ip1)
  (geo-lookup prvd ip2)
  (geo-lookup prvd ip3)

  (def prvd (stop-provider prvd))

  (start-provider!)

  (geo-lookup ip1)
  (geo-lookup ip2)
  (geo-lookup ip3)

  (stop-provider!)

  )
