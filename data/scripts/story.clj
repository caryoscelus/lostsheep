(ns story)

(def story-stage :contact)

(def know-mechanic false)

(bind "mechanic"
      (fn []
          (cond
              (not know-mechanic) (run "mechanic-contact")
              :else (run "mechanic-empty"))))

(bind "mechanic-contact" (t "
[Mechanic description]
"
(list
    (ln "..." nil))))

(bind "mechanic-empty" (t "
I approached mechanic..
"
(list
    (ln "..." nil))))
