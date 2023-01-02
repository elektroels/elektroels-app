(ns ^:figwheel-hooks elektroels.app
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

(println "This text is printed from src/elektroels/app.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b] (* a b))

;; define your app data so that it doesn't get over-written on reload
(def app-state (atom {:headline "Welcome"
                      :part1 "Troels H is a creative programmer with a deep technical background."
                      :part2 "An it engineer who studied art history.
                              Lives in Copenhagen, drinks, rides, paints,
                              codes, thinks, listens, dreams etc. etc."
                      :phone-number "+45 42 71 22 72"
                      :email "elektroels @ gmail.com"
                      :github "https://github.com/elektroels"
                      :meetup "http://www.meetup.com/members/186263611/"
                      :twitter "https://twitter.com/elektroels"
                      :linkedin "https://dk.linkedin.com/in/troels-henningsen-b0166763"
                      :facebook "https://www.facebook.com/elektroels"
                      :position 0
                      :images '({:name "ole" :src "/images/olemange.png"}
                                {:name "krokdodille" :src "/images/TurkisFrikodiler1.jpg"}
                                {:name "gaerdesmutte" :src "/images/Gaerdesmutte.JPG"}
                                {:name "sort-sky" :src "/images/SortSkymedgobler.jpg"}
                                {:name "turtle" :src "/images/Turtle.jpg"}
                                {:name "dame" :src "/images/Dame2.JPG"}
                                {:name "goble" :src "/images/Goble.jpg"}
                                {:name "monika" :src "/images/Monika.JPG"}
                                {:name "til-dennis" :src "/images/TilDennis.jpg"})}))

(defn get-app-element []
  (gdom/getElement "app"))

(defn left-image []
  (if (> (:position @app-state) 0)
    (swap! app-state update :position - 1)
    (swap! app-state assoc :position (dec (count (:images @app-state))))))

(defn right-image []
  (if (< (:position @app-state) (dec (count (:images @app-state))))
    (swap! app-state update :position + 1)
    (swap! app-state assoc :position 0)))


(defn hello-world []
  [:div

   [:div.block.bg-darktone-three
    [:div.center-column
     [:h1 (:headline @app-state)]
     [:p (:part1 @app-state)]
     [:p (:part2 @app-state)]]]

   [:div.block
    [:div.images
     [:div 
      [:i.left {:on-click left-image}]]
     [:img {:src (:src (nth (:images @app-state) (:position @app-state)))}]
     [:div 
      [:i.right {:on-click right-image}]]]]

   [:div.block.bg-darktone-three
    [:div.center-column
    [:p (:phone-number @app-state)]
    [:p (:email @app-state)]
    [:a.social {:href (:github @app-state)} "github"]
    [:a.social {:href (:meetup @app-state)} "meetup" ]
    [:a.social {:href (:twitter @app-state)} "twitter" ]
    [:a.social {:href (:linkedin @app-state)} "linkedin" ]
    [:a.social {:href (:facebook @app-state)} "facebook" ]]]

   ])

(defn mount-hello [el]
  (reagent/render-component [hello-world] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount-hello el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
