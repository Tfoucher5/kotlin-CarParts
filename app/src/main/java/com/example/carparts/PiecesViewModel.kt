package com.example.carparts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PiecesViewModel : ViewModel() {

    private val _pieces = MutableLiveData<List<Pieces>>()
    private val _favorisList = MutableLiveData<List<Boolean>>()

    val pieces: LiveData<List<Pieces>> get() = _pieces
    val favorisList: LiveData<List<Boolean>> get() = _favorisList

    init {
        _pieces.value = listOf(
            Pieces(
                "Arbre à cames",
                "L'arbre à cames est une pièce mécanique utilisée, principalement, dans des moteurs à pistons à quatre temps pour la commande synchronisée des soupapes. Il scompose d'une tige cylindrique disposant d'autant de cames que de soupapes à commander indépendamment ou par groupe, glissant sur la queue de soupape, ou sur un renvoi mécanique.",
                "arbre___cames",
                "https://fr.wikipedia.org/wiki/Arbre_à_cames",
                62,
                424,
                48.2082,
                16.3738 // Vienne, Autriche
            ),
            Pieces(
                "Injecteur",
                "Composant qui pulvérise le carburant sous pression dans la chambre de combustion pour une meilleure combustion.",
                "injecteur",
                "https://fr.wikipedia.org/wiki/Injecteur#:~:text=Injecteur%20de%20moteur,-Animation%20d%27un&text=L%27injecteur%20permet%20l%27apport,essence%20qui%20retient%20les%20impuretés.",
                14,
                70,
                40.7128,
                -74.0060 // New York, USA
            ),
            Pieces(
                "Bougie d'allumage",
                "Élément qui génère une étincelle pour enflammer le mélange air-carburant dans un moteur essence.",
                "bougie",
                "https://fr.wikipedia.org/wiki/Bougie_d%27allumage",
                13,
                40,
                34.0522,
                -118.2437 // Los Angeles, USA
            ),
            Pieces(
                "Piston",
                "Composant mobile dans le cylindre qui comprime le mélange air-carburant et transmet la force de l'explosion au vilebrequin.",
                "piston",
                "https://fr.wikipedia.org/wiki/Piston_(mécanique)#:~:text=Le%20piston%20est%20l%27élément,un%20mouvement%20rotatif%20du%20vilebrequin.",
                75,
                55,
                48.8566,
                2.3522 // Paris, France
            ),
            Pieces(
                "Vilebrequin",
                "Pièce tournante qui convertit le mouvement linéaire des pistons en mouvement rotatif pour entraîner les roues.",
                "vilebrequin",
                "https://fr.wikipedia.org/wiki/Vilebrequin_(moteur)#:~:text=Le%20vilebrequin%20est%20un%20dispositif,de%20rotation%20continu%2C%20et%20inversement.",
                60,
                500,
                -33.8688,
                151.2093 // Sydney, Australie
            ),
            Pieces(
                "Bloc-cylindres",
                "Le bloc-cylindres est la structure principale du moteur qui abrite les cylindres et d'autres composants essentiels. Il est généralement fabriqué en fonte ou en aluminium pour résister aux hautes températures et pressions générées lors de la combustion.",
                "bloccylindre",
                "https://fr.wikipedia.org/wiki/Bloc-cylindres",
                300,
                250,
                55.7558,
                37.6173 // Moscou, Russie
            ),
            Pieces(
                "Culasse",
                "La culasse est située au-dessus du bloc-cylindres et renferme les chambres de combustion, les soupapes et, dans certains cas, les arbres à cames. Elle assure l'étanchéité des cylindres et participe au processus de combustion.",
                "culasse",
                "https://fr.wikipedia.org/wiki/Culasse",
                250,
                300,
                35.6895,
                139.6917 // Tokyo, Japon
            ),
            Pieces(
                "Collecteur d'admission",
                "Le collecteur d'admission distribue le mélange air-carburant aux cylindres du moteur. Il est conçu pour optimiser le flux d'air et améliorer les performances du moteur.",
                "collecteuradmission",
                "https://fr.wikipedia.org/wiki/Collecteur_d%27admission",
                200,
                600,
                41.9028,
                12.4964 // Rome, Italie
            ),
            Pieces(
                "Collecteur d'échappement",
                "Le collecteur d'échappement recueille les gaz brûlés provenant des cylindres et les dirige vers le système d'échappement. Il est conçu pour résister à des températures élevées et minimiser les pertes de pression.",
                "collecteurechappement",
                "https://fr.wikipedia.org/wiki/Collecteur_d%27%C3%A9chappement",
                200,
                500,
                51.5074,
                -0.1278 // Londres, Royaume-Uni
            ),
            Pieces(
                "Carter d'huile",
                "Le carter d'huile est un réservoir situé sous le moteur qui contient l'huile de lubrification. Il protège l'huile des contaminants extérieurs et aide à dissiper la chaleur générée par le moteur.",
                "carterhuile",
                "https://fr.wikipedia.org/wiki/Carter_d%27huile",
                300,
                400,
                39.9042,
                116.4074 // Pékin, Chine
            )
        )
        _favorisList.value =
            List(_pieces.value?.size ?: 0) { false }
    }

    fun updateFavoris(newFavorisList: List<Boolean>) {
        _favorisList.value = newFavorisList
    }

    fun addPiece(piece: Pieces) {
        val currentPieces = _pieces.value ?: emptyList()

        val updatedPieces = currentPieces + piece

        _pieces.value = updatedPieces
    }

}
