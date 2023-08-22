(ns ip-geoloc.util)

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

(defn ip-conversion [ip]
  (if (int? ip)
    (int-to-ip ip)
    ip))
