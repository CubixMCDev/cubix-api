// Le plugin de conventions est chargé une seule fois ici : sans ça, Spotless partage mal son
// service entre sous-projets voisins.
plugins {
    id("cubix.java-conventions") apply false
}
