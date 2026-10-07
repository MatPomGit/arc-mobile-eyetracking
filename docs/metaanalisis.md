Metaanaliza metod śledzenia wzroku na urządzeniach mobilnych

Streszczenie

Śledzenie wzroku („eye tracking”) na smartfonach i tabletach ewoluowało od prototypów wymagających dodatkowego sprzętu (zewnętrzne kamery/komputery) do algorytmów działających na „niezmodyfikowanych” urządzeniach, głównie w oparciu o przednią kamerę RGB oraz coraz częściej o czujniki głębi (RGB‑D) i NIR/IR. W literaturze dominują podejścia appearance‑based (uczenie odwzorowania obraz→spojrzenie), które dzięki dużym zbiorom danych (np. GazeCapture) i sieciom CNN osiągnęły błędy rzędu ~1–3 cm na ekranie, przy czym personalizacja/kalibracja potrafi znacząco obniżyć błąd (np. do ~0.46 cm w warunkach „optymalnego UX”). [1] Kluczową barierą praktyczną wciąż pozostaje utrzymanie dokładności w ruchu (zmiana postawy, odległości, orientacji telefonu i głowy) oraz koszt obliczeniowy (latencja, energia, pamięć). Nowsze prace mobilne pokazują, że (a) modele temporalne (CNN+GRU/LSTM) poprawiają estymację dla bodźców dynamicznych, (b) fuzja RGB+Depth redukuje błąd w zróżnicowanych kontekstach użycia, a (c) czujniki inercyjne (IMU) mogą uruchamiać ciągłą rekalkibrację/uczenie ciągłe w odpowiedzi na wykrytą zmianę ruchu. [2] Metaanaliza ilościowa jest ograniczona przez silną heterogeniczność: różne zbiory danych, definicje błędu (cm vs stopnie), protokoły kalibracji, odległości obserwacji i zakresy ruchu. ‍Literatura przeglądowa podkreśla brak standaryzacji metryk i procedur oceny, co utrudnia „uczciwe” porównania między pracami. [3]

Metodologia przeglądu i ramy porównania

Zakres raportu obejmuje metody śledzenia punktu spojrzenia na ekranie (2D PoG w cm) i/lub kierunku spojrzenia (3D, błąd kątowy w °) na urządzeniach mobilnych (smartfony, tablety). Rdzeń przeglądu oparto na: (a) publikacjach przeglądowych i benchmarkach (m.in. przegląd mobilny Lei i in.; przekrojowy przegląd konsumenckich platform Kar & Corcoran; przegląd/benchmark deep‑learning appearance‑based Cheng i in.), (b) kluczowych pracach źródłowych dla mobilnej estymacji spojrzenia (Krafka/iTracker, Valliappan/Google Research, RGBDGaze, prace o bodźcach dynamicznych i optymalizacji na edge), oraz (c) wybranych patentach dot. gaze tracking w urządzeniach mobilnych. [4]

Do porównań przyjęto następujące osie oceny (zaznaczając braki raportowania, gdy występują): dokładność (RMSE/średni błąd w cm na ekranie lub błąd kątowy w °), szybkość (FPS lub latencja/inference time), złożoność (liczba parametrów, ewentualnie FLOPs/pamięć), wymagania kalibracyjne (brak/implicit/explicit; koszt czasowy), odporność na oświetlenie i pozycję głowy/urządzenia, oraz sensorium (RGB, RGB‑D, NIR/IR, IMU). [5]

Wątek „metaanalityczny” zrealizowano w praktyce jako: (i) analizy podgrupowe w obrębie tych samych zbiorów danych (np. DynamicGaze), (ii) porównania względne (procentowa poprawa vs baseline), oraz (iii) wykorzystanie testów istotności raportowanych w pracach (np. ANOVA+Tukey dla modeli na DynamicGaze). Pełna metaanaliza standaryzowana (np. efekt ujednolicony) jest zwykle niemożliwa bez dostępu do surowych rozkładów błędów lub jednolitych protokołów oceny. [6]
Przegląd literatury i taksonomia metod

Kontekst mobilny i „oś czasu”

Przegląd MobileHCI wskazuje, że badania nad eye trackingiem na urządzeniach ręcznych zaczęły się już ok. 2002 r.; do ok. 2010 r. dominowały podejścia wymagające zewnętrznego sprzętu do przetwarzania w czasie rzeczywistym, po czym wzrost jakości kamer i mocy obliczeniowej umożliwił eye tracking „w pełni na urządzeniu” (lub z ograniczonym wsparciem edge). [7] W kolejnej fazie przełomem stały się: duże zbiory danych z crowdsourcingu (GazeCapture) i modele end‑to‑end (iTracker), a następnie przeniesienie ciężaru na personalizację, modele lekkie oraz odporność na dynamikę użycia (ruch, zmiany postawy, zmienne oświetlenie). [8]
timeline

  title Kamienie milowe mobilnego eye trackingu (wybór)
  2002 : Wczesne prace MobileHCI nad gaze na handheldach (często z dodatkowym sprzętem)
  2016 : GazeCapture + iTracker: CNN dla telefonów/tabletów, 10–15 fps na urządzeniu
  2018 : Survey MobileHCI: „past–present–future” gaze-enabled handhelds
  2020 : Smartphone eye tracking dla badań okulomotorycznych: personalizacja do ~0.46 cm, model ~170k parametrów
  2022 : RGBDGaze: fuzja RGB+Depth na smartfonach (iPhone TrueDepth) i konteksty użycia (chodzenie/siedzenie itd.)
  2023 : End-to-end review handheld: workflow, sensory (RGB/RGB-D/IR), kalibracja, modele (CNN/RNN/Transformers)
  2024-2025 : DynamicGaze + CNN+RNN, pomiary latencji i optymalizacje (quantization/pruning), edge intelligence
  2025 : Motion-aware continual calibration z IMU (MAC-Gaze): redukcja błędu w ruchu
Źródła dla wydarzeń: przegląd mobilny oraz prace kamieniowe. [9]

Główne klasy metod

W literaturze konsumenckiej i mobilnej najczęściej wyróżnia się trzy rodziny: (1) model‑based (geometria oka, glinty/NIR, PCCR), (2) feature‑based (regresja na cechach typu źrenica/kąciki oczu), (3) appearance‑based (głębokie uczenie obraz→spojrzenie); współcześnie dominuje (3) ze względu na dostępność kamer RGB i danych oraz skalowalność. [10] Dla platform mobilnych kluczowe determinanty jakości to m.in. zakres ruchów głowy i urządzenia, odległość użytkownik–kamera, oświetlenie, zasłonięcie oczu, okulary oraz ograniczenia energetyczno‑obliczeniowe. [11]

Architektury i sensory dla mobilnego gaze trackingu

Appearance-based: CNN i warianty mobilne

Klasyczny wzorzec mobilny to model wielowejściowy uczący się bezpośrednio regresji PoG na ekranie z: wycinka twarzy, wycinków oczu oraz „face grid” (maski wskazującej pozycję głowy w kadrze). iTracker (Krafka i in.) trenowany na GazeCapture raportuje błąd ~1.71 cm na telefonach i ~2.53 cm na tabletach bez kalibracji; z kalibracją błąd spada odpowiednio do ~1.34 cm i ~2.12 cm, a szybkość działania na urządzeniu klasy mobilnej to ok. 10–15 fps. [12]

Przegląd handheld Lei i in. pokazuje, że wiele reprezentatywnych modeli appearance‑based buduje się na CNN (VGG/ResNet, ale też MobileNet‑V2 jako architektura „mobile‑friendly”), a nowsze prace wprowadzają również Transformers (np. GazeTR/ViT w kontekście benchmarków). [13] W praktyce mobilnej często stosuje się też pipeline: detekcja twarzy + standaryzacja (crop/resize/normalizacja) + estymacja PoG, co jest istotne, bo etap detekcji twarzy może dominować czas wykonania. [14]

Temporalne: CNN + GRU/LSTM oraz motywacja dla bodźców dynamicznych

W scenariuszach mobilnych (wideo, gry, AR) bodźce i ruch użytkownika wprowadzają korelacje czasowe, które modele pojedynczej klatki (frame‑based) tracą. W pracy o DynamicGaze zaproponowano trzy architektury: CNN (baseline) oraz CNN+GRU i CNN+LSTM, osiągając na DynamicGaze RMSE odpowiednio ~1.468 cm (CNN), ~1.091 cm (CNN+GRU) i ~0.955 cm (CNN+LSTM); autorzy raportują istotne statystycznie różnice (ANOVA F(3,396)=254.42, p<0.001; Tukey: CNN+LSTM najlepszy). [15] To empirycznie wspiera tezę, że jawne modelowanie czasu pomaga w warunkach dynamicznych, choć okupione jest zwykle większą latencją i złożonością. [14]

Model-based: geometria oka, PCCR i powiązanie z NIR/IR

Model‑based gaze tracking w wersji klasycznej wykorzystuje NIR LED do generowania refleksów (glints) na rogówce i estymuje spojrzenie z relacji między pozycją źrenicy a glintami; to podejście znane jako PCCR jest powszechne w komercyjnych eye trackerach. [16] W urządzeniach mobilnych w „czystej” postaci jest trudniejsze (brak dedykowanego oświetlenia i stabilnej geometrii), ale staje się realniejsze wraz z obecnością czujników NIR/IR i/lub głębi w niektórych smartfonach. [17]

Hybrydowe: łączenie appearance i cech geometrycznych

EasyGaze jest przykładem podejścia hybrydowego: detekcja twarzy/oczu służy do znalezienia punktów cech (feature points), a następnie wyznacza się wektory (np. kącik–źrenica) używane do obliczenia współrzędnych fiksacji; raportowana średnia dokładność to ok. 1.93° przy rozdzielczości obrazu 96×48 px w sprzyjającym oświetleniu (z przodu). [18] Hybrydy często interpretować można jako „CNN do ekstrakcji/punktów + geometria do mapowania”, co bywa korzystne, gdy chcemy kontrolować zachowanie modelu poza rozkładem treningowym (np. nietypowe odległości), ale zależy od stabilności detekcji punktów w realnym oświetleniu. [19]

Lightweight/edge-optimized: pruning, quantization, distillation

W smartfonowym eye trackingu krytyczna jest latencja i koszt energetyczny. W pracy o DynamicGaze i „edge intelligence” mierzone inference time na telefonie Samsung S22 dla modeli TFLite to średnio ok. 742 ms (iTracker), 256 ms (CNN), 415 ms (CNN+GRU) i 426 ms (CNN+LSTM), przy zastrzeżeniu, że to „czysty” czas modelu bez przechwytywania klatek i preprocessing’u. [14] Autorzy testują też optimizacje: quantization i pruning, pokazując, że na przykład na Intel NUC (edge) quantization może znacząco skrócić czas inferencji, ale zwykle zwiększa RMSE (np. CNN+LSTM: 0.955→1.192 cm po quantization, a do 1.366 cm po pruning w ich eksperymencie). [20] Z punktu widzenia praktyk wdrożeniowych, dokumentacja TensorFlow Lite opisuje post‑training quantization jako narzędzie redukcji latencji i zużycia energii, kosztem potencjalnego spadku dokładności. [21]

Knowledge distillation jest ogólną rodziną technik kompresji „teacher→student” szeroko opisaną w literaturze (strategie, typy wiedzy, schematy uczenia), a nowsze prace zaczynają stosować distillation wprost do celów on‑device gaze estimation (np. preprint DistillGaze). [22] W mobilnym eye trackingu distillation jest szczególnie atrakcyjne, gdy „teacher” może być dużym modelem trenowanym na danych syntetycznych/heterogenicznych, a „student” ma działać na TFLite/CoreML w ograniczonym budżecie pamięci. [23]

Fuzja sensorów: RGB, RGB‑D, NIR/IR, IMU

RGB pozostaje standardem, bo jest powszechny i tani; większość prac mobilnych w przeglądach handheld bazuje na RGB (front‑facing). [24]

RGB‑D (czujniki głębi w telefonach) umożliwia jawne uwzględnienie odległości i orientacji twarzy; RGBDGaze wykorzystuje kamerę RGB i czujnik głębi TrueDepth (iPhone X+) i pokazuje redukcję błędu: 1.89 cm dla modelu multimodalnego vs 2.26 cm dla samego RGB (16.3% poprawy) w różnych kontekstach (stanie, chodzenie, siedzenie, leżenie). [25]

NIR/IR – przegląd handheld zauważa wzrost liczby telefonów z kamerami NIR (m.in. iPhone i wybrane marki Android), co sprzyja stabilniejszej detekcji oka przy trudnym oświetleniu i może przybliżać mobilne systemy do jakości rozwiązań PCCR. [26]

IMU – dane inercyjne pomagają wykrywać zmiany ruchu/postawy urządzenia i inicjować rekalkibrację lub adaptację modelu. MAC‑Gaze łączy wizualny gaze estimator z modelem rozpoznania aktywności z IMU, uruchamiając ciągłą kalibrację i redukując błąd m.in. na RGBDGaze (1.73→1.41 cm) oraz na MotionGaze (2.81→1.92 cm). [27] W warstwie „systemowej” podobną intuicję widać także w patentach: np. US20160282937A1 zakłada identyfikację ruchu obrotowego urządzenia (np. z żyroskopu) i łączenie jej z modelowaniem 3D twarzy, potencjalnie także z użyciem kamery IR. [28]

flowchart TD
  A[Sensor wejściowy] --> B[Detekcja twarzy/oczu + landmarki]
  B --> C[Normalizacja: crop/resize/rectification]
  C --> D{Model estymacji}
  D -->|Appearance-based| E[CNN / CNN+RNN / Transformer]
  D -->|Model-based| F[Geometria oka, PCCR, 3D model + PnP]
  D -->|Hybrid| G[Punkty cech + regresja/geometryczne mapowanie]
  E --> H[Kalibracja / personalizacja]
  F --> H
  G --> H
  H --> I[Post-processing: filtracja, wygładzanie, outlier removal]
  I --> J[Wyjście: PoG 2D (cm) lub kierunek 3D (°)]
  J --> K[Detekcja zdarzeń: fiksacje/sakkady/pursuit/blinks]
  K --> L[Aplikacje: HCI, UX, zdrowie, dostępność, AR/gry]
  A --> M[IMU (opcjonalnie)]
  M --> H
  A --> N[Depth/NIR (opcjonalnie)]
  N --> C


Rozwiązania komercyjne i open-source

Komercyjne i „research-grade” ekosystemy

Tobii Pro Glasses 3 to przykład mobilnego (nagłownego) rozwiązania badawczego; producent opisuje stosowanie PCCR (pupil center + corneal reflection) do wyznaczania punktu spojrzenia oraz deklaruje rejestrację „raw eye tracking data” do 100 Hz, co ma wystarczać do większości zjawisk takich jak fiksacje i miary źrenicy. [29] Tobii udostępnia Tobii Pro SDK do budowy aplikacji analitycznych, w tym strumienie typu gaze position, gaze origin, pupil size oraz synchronizację z urządzeniami zewnętrznymi (zależnie od trackera). [30]

Pupil Labs oferuje zarówno platformę open‑source (Pupil Core) z repozytorium kodu, jak i nowsze urządzenia mobilne (np. Neon) oraz dokumentację strumieni danych; Pupil Core jest rozwijany jako otwarta platforma, a producent udostępnia specyfikacje i raporty walidacyjne (np. Neon accuracy test report na Zenodo). [31]

Warto podkreślić różnicę: raport dotyczy głównie eye trackingu „na urządzeniu mobilnym” (smartfon/tablet), natomiast wiele komercyjnych systemów „mobilnych” to eye trackery nagłowne, które mogą być używane w badaniach z użyciem smartfona, ale nie są to algorytmy działające wyłącznie na froncie telefonu. [32]

Open-source i narzędzia badawcze

W obszarze software’owym i replikowalności istotne są:

OpenGaze – toolkit „dla projektantów interfejsów” mający demokratyzować użycie appearance‑based gaze estimation w HCI, z implementacjami metod i komponentów interakcyjnych. [33]

WebGazer.js – otwarta biblioteka eye trackingu w przeglądarce, samokalibrująca się na podstawie interakcji użytkownika; bywa używana również na urządzeniach mobilnych via kamera frontowa, choć zwykle z ograniczoną dokładnością względem rozwiązań badawczych. [34]

GazeML – framework (repozytorium) integrujące implementacje opublikowanych algorytmów gaze estimation, użyteczne jako „warsztat” porównawczy (z zastrzeżeniem jakości re‑implementacji). [35]

GazeCapture/iTracker – projekt i dane (GazeCapture) oraz referencyjna praca iTracker stanowią punkt odniesienia dla mobilnej estymacji spojrzenia na RGB. [36]

RGBDGaze – repozytorium kodu i link do datasetu (RGB+Depth) dla smartfonów, wprost adresujące zmienność kontekstu użycia. [37]

MobileEye (Gunawardena i in.) – autorzy podają dostępność kodu i modeli dla smartfonowego eye trackingu na bodźcach dynamicznych. [38]

Metryki, tabela porównawcza i metaanaliza wyników

Metryki porównawcze i problemy porównywalności

Najczęściej raportowane są: błąd na ekranie (cm, np. średnia odległość euklidesowa / RMSE) oraz błąd kątowy (°) dla gaze direction. [39] W praktyce „ten sam” błąd cm nie jest w pełni porównywalny bez informacji o: rozmiarze ekranu, odległości obserwacji (cm), protokole kalibracji, typie bodźca (statyczny punkt vs moving dot vs real content), oraz czy błąd liczono na całej sekwencji czy po odrzuceniu np. pierwszych 800 ms po pojawieniu się bodźca (saccade latency). [40]
W dodatku część prac raportuje tylko inference time modelu, a część pełną latencję pipeline’u (capture + face detect + preprocess + infer), co prowadzi do istotnie różnych wniosków o „real‑time”. [41]

Tabela porównawcza metod i implementacji

Poniżej zestawiono reprezentatywne metody/implementacje (akademickie i systemowe) raportujące metryki mobilne. Puste pola oznaczają brak danych w cytowanym źródle lub nieporównywalność raportowania.

Metoda / system	Klasa	Sensory	Dane / kontekst	Kalibracja	Dokładność (przykład)	Szybkość / latencja	Złożoność (param.)	Uwagi o odporności
iTracker (Krafka 2016)	CNN appearance	RGB	GazeCapture (telefony/tablety)	opcjonalna	1.71 cm (phone) i 2.53 cm (tablet) bez kalibr.; 1.34 / 2.12 cm z kalibr. [42]	10–15 fps na mobilnym urządzeniu [43]	(nie raport w tej pracy)	wrażliwy na head pose/occlusions typowe dla mobile
Smartphone eye tracking (Valliappan 2020)	CNN + personalizacja	RGB + landmarki	telefon (Pixel 2 XL), zadania okulomotoryczne	tak (≈30 s; ~100 klatek kal.)	1.92±0.20 cm base → 0.46±0.03 cm po personalizacji; percentyle [5,95]=[0.31,0.72] cm [44]	(online możliwe; analizy też offline) [45]	~170k parametrów (lekki) [45]	spadek jakości przy większym pan/tilt/roll i dystansie [45]
DynamicGaze: CNN (Gunawardena 2024/25)	CNN appearance	RGB	DynamicGaze (moving dot, 42 modele tel.)	brak w raporcie	RMSE 1.468 cm (DynamicGaze) [46]	255.67 ms/model na S22 (bez pipeline) [46]	3.210M [46]	detekcja twarzy może dominować pipeline [47]
DynamicGaze: CNN+GRU	CNN+RNN temporal	RGB	j.w.	brak w raporcie	RMSE 1.091 cm (DynamicGaze) [46]	414.81 ms/model na S22 [46]	3.343M [46]	lepsze dla bodźców dynamicznych [46]
DynamicGaze: CNN+LSTM	CNN+RNN temporal	RGB	j.w.	brak w raporcie	RMSE 0.955 cm (DynamicGaze); najlepszy wg ANOVA/Tukey [48]	426.18 ms/model na S22 [46]	3.344M [46]	kompromis accuracy–latency; wrażliwy na optymalizacje [20]
RGBDGaze (Arakawa 2022)	multimodal CNN	RGB + Depth	4 konteksty (chodzenie/siedzenie/…)	(w badaniu porównywane różne protokoły)	mean error 1.89 cm; RGB‑only 2.26 cm (−16.3%) [25]	(brak FPS w abstrakcie)	(brak param. w abstrakcie)	adaptacja do dystansu i orientacji twarzy [25]
EasyGaze (2022)	hybrid	RGB	handheld; testy oświetlenia/rozdz.	(niejednozn.)	~1.93° przy 96×48 i oświetleniu frontalnym [18]	(brak)	(brak)	silna zależność od warunków oświetlenia [18]
MAC‑Gaze (2025)	system adaptacyjny	RGB(+Depth) + IMU	RGBDGaze + MotionGaze	„continual calibration”	RGBDGaze: 1.73→1.41 cm; MotionGaze: 2.81→1.92 cm [27]	(brak publicznej latencji w streszczeniu)	(brak)	wykrywa motion‑states i uruchamia rekalkibrację [49]
Apple ARKit (jako baseline w RGBDGaze)	SDK / model‑based-ish	(TrueDepth/NIR zależnie od urządzenia)	w porównaniu RGBDGaze	(wbudowane)	6.38 cm (wg tabeli porównawczej RGBDGaze) [25]	(brak)	(n/d)	oferuje m.in. lookAtPoint i transformacje oczu [50]

Analiza statystyczna i „metaanaliza” na dostępnych danych

Podgrupa: DynamicGaze (jedno źródło danych, jednolita metryka)

DynamicGaze podaje RMSE (cm) dla czterech modeli na tym samym zbiorze; to rzadki przypadek umożliwiający porównanie w obrębie jednolitego protokołu. Na DynamicGaze modele temporalne poprawiają wynik względem CNN:
    • poprawa CNN+LSTM vs CNN: (1.468 − 0.955) / 1.468 ≈ 34.9% redukcji RMSE,
    • poprawa CNN+GRU vs CNN: (1.468 − 1.091) / 1.468 ≈ 25.7% redukcji RMSE. [46]

Autorzy raportują test istotności: ANOVA wskazuje istotne różnice RMSE między modelami (F(3,396)=254.42, p<0.001), a Tukey HSD wskazuje CNN+LSTM jako istotnie lepszy od CNN, CNN+GRU i iTracker w ich warunkach. [51]

Trade-off accuracy–latency: wyniki on-device (S22) i edge

W tym samym badaniu inference time na Samsung S22 (TFLite) pokazuje, że uzyskanie <33 ms/klatkę (≈30 FPS) jest dalekie dla rozważanych modeli (255–742 ms/model), nawet przed doliczeniem detekcji twarzy i preprocessingu. [14] Z kolei eksperymenty edge pokazują, że optymalizacja i mocniejszy „nearby compute” może zbliżać się do bardziej interaktywnych czasów całkowitych, ale face detection nadal jest istotnym składnikiem (np. setki ms na słabszych urządzeniach). [41]

Wpływ personalizacji (Valliappan 2020) jako „boxplot” jakości

Dane Valliappan i in. umożliwiają częściową analizę rozkładu błędu po personalizacji: średnio 0.46 cm, z najlepszym uczestnikiem 0.23 cm, najgorszym 0.75 cm oraz percentylami [5,95]=[0.31,0.72] cm. [45] To sugeruje, że nawet przy podobnym protokole kalibracji, istnieje znaczna wariancja międzyosobnicza, istotna dla projektowania „quality control” (np. odrzucanie użytkowników z >1 cm na hold‑out). [45]

xychart-beta
  title "RMSE (cm) na DynamicGaze: wpływ modelowania czasu"
  x-axis ["CNN", "CNN+GRU", "CNN+LSTM", "iTracker"]
  y-axis "RMSE (cm)" 0 --> 1.6
  bar [1.468, 1.091, 0.955, 1.499]
Źródło wartości: tabela wyników DynamicGaze. [46]

xychart-beta
  title "Inference time (ms) na Samsung S22 (model-only, TFLite)"
  x-axis ["CNN", "CNN+GRU", "CNN+LSTM", "iTracker"]
  y-axis "ms" 0 --> 800
  bar [255.67, 414.81, 426.18, 742.22]
Uwaga: bez czasu przechwytywania klatki, detekcji twarzy i preprocessingu. [14]

Detekcja ruchów oczu i analiza zdarzeń w strumieniu danych

Rodzaje zdarzeń okulomotorycznych

W analizie eye‑trackingowej standardowo rozróżnia się m.in.: fiksacje (okresy względnej stabilizacji spojrzenia), sakkady (szybkie przerzuty spojrzenia między fiksacjami), sekwencje fiksacja–sakkada (scanpath), a także miary źrenicy i mrugnięcia jako wskaźniki obciążenia poznawczego. [52] Dla urządzeń mobilnych dochodzi wyraźniej problem rozdzielenia ruchu oka od ruchu głowy i od ruchu urządzenia, co wpływa na detekcję zdarzeń w danych niskiej częstotliwości i obarczonych szumem. [53]

Klasyczne algorytmy detekcji zdarzeń

Salvucci i Goldberg proponują taksonomię algorytmów identyfikacji fiksacji/sakkad i omawiają rodzinę metod opartych o progi czasowo‑przestrzenne. [54] Najczęściej spotyka się:
  
- I‑DT (dispersion‑threshold): grupuje próbki, które mieszczą się w ograniczonej dyspersji przestrzennej przez minimalny czas (dobre dla fiksacji, wrażliwe na próg i rozdzielczość). [55]

- I‑VT (velocity‑threshold): klasyfikuje próbki na podstawie prędkości kątowej/spojrzenia, rozdzielając „wolne” fiksacje od szybkich sakkad; w mobilnych systemach bywa używany jako prosty filtr zdarzeń. [56]

- HMM / metody probabilistyczne: modelują stany (fiksacja/sakkada/…) i przejścia między nimi, co bywa bardziej odporne na szum, ale zwiększa koszt obliczeniowy i wymaga strojenia. [57]

W badaniu smartphone eye tracking Valliappan i in. klasyfikowano zdarzenia (sakkady/fiksacje) z progiem prędkości 22°/s, wskazując na mobilny, pragmatyczny kompromis: niski koszt i działanie na danych 30 Hz, kosztem ograniczonej czułości na mikro‑ruchy. [45]
Detekcja microsakkad i ograniczenia mobilne

Microsakkady wymagają zwykle wysokiej częstotliwości próbkowania i niskiego szumu; klasyczny algorytm Engbert–Kliegl opiera się o próg prędkości skalowany odchyleniem standardowym prędkości w oknie czasowym. [58] Smartfony pracujące na 30–60 Hz (często mniej stabilnie) zwykle nie dostarczają jakości porównywalnej z trackerami 200–1000+ Hz, przez co wiarygodna detekcja microsakkad na samym RGB bywa trudna lub niestabilna; literatura mobilna częściej koncentruje się na fiksacjach, sakkadach i miernikach „saliency‑like”. [59]

Skuteczność i koszty obliczeniowe detektorów zdarzeń

Porównania algorytmów detekcji zdarzeń pokazują, że różne metody (progowe i bardziej złożone) mogą dawać znacząco różne klasyfikacje nawet na tym samym sygnale; dlatego zaleca się raportować parametry algorytmu i weryfikować je w zależności od zadania. [60] W kontekście mobilnym rekomenduje się:

- dla interakcji w czasie rzeczywistym: proste I‑VT / adaptacyjne progi prędkości (niskie koszty, łatwe do wdrożenia na edge), [61]

- dla analiz offline: bardziej rozbudowane metody (adaptacyjne, HMM) oraz walidacje jakości (np. porównanie do ręcznej anotacji lub do eye trackera IR). [62]


Ograniczenia badań, luki i rekomendacje

Najważniejsze ograniczenia obecnej literatury i wdrożeń to:
Brak standaryzacji oceny i „nieporównywalność” wyników: przeglądy podkreślają rozjazd w metrykach (cm vs °), protokołach pre/post‑processingu, konwersjach 2D/3D oraz raportowaniu dokładności i robustności, co utrudnia formalną metaanalizę między pracami. [3]

„Real‑world motion gap”: dane treningowe często nie obejmują realistycznego zakresu ruchu i postaw (chodzenie, leżenie, trzymanie telefonu pod nietypowym kątem). Prace empiryczne pokazują spadek jakości przy zmianach head pose i orientacji urządzenia; dlatego rośnie znaczenie RGB‑D, IMU i adaptacyjnych metod rekalkibracji/continual learning. [63]

Bottlenecks obliczeniowe: nawet jeśli sam model jest umiarkowany, pipeline (zwłaszcza detekcja twarzy) może dominować. Wyniki DynamicGaze pokazują, że czasy inference modelu na telefonie są rzędu setek ms i same w sobie nie gwarantują interaktywności; edge offloading i optymalizacje (quantization) pomagają, ale zwykle wprowadzają spadek dokładności. [64]

Kalibracja vs użyteczność: personalizacja potrafi radykalnie poprawiać dokładność (np. ~0.46 cm przy ~30 s kalibracji), ale zwiększa tarcie UX i komplikuje scenariusze masowe. Niezbędne są lepsze metody kalibracji implicit/continual oraz kontrola jakości (detekcja driftu, automatyczne „re‑calibration triggers”). [65]

Rekomendacje badawcze i inżynierskie:

- raportować pełny „system latency budget” (capture + face/eye detect + preprocess + model) oraz energię/pamięć, a nie tylko inference time sieci; [41]
- wprowadzać benchmarki mobilne obejmujące realne postawy i ruch (jak RGBDGaze/DynamicGaze/MotionGaze) oraz jawnie raportować zakres odległości i head‑pose; [66]
- projektować modele „motion‑aware”: fuzja RGB‑D, IMU‑triggered continual calibration, a także interpretowalne hybrydy (cechy + CNN) dla lepszej kontroli poza rozkładem; [67]
- dla wdrożeń: agresywnie stosować kwantyzację/pruning/distillation, ale zawsze raportować degradację RMSE i odporności w scenariuszach „in the wild”, bo trade‑off bywa nieliniowy (np. większy spadek dla modeli rekurencyjnych). [68]
Referencje

    1. Lei Y, He S, Khamis M, Ye J. An End-to-End Review of Gaze Estimation and its Interactive Applications on Handheld Mobile Devices. ACM Comput Surv. 2023. doi:10.1145/3606947. [69]
    2. Krafka K, Khosla A, Kellnhofer P, et al. Eye Tracking for Everyone. Proc CVPR. 2016. [70]
    3. Valliappan N, Dai N, Steinberg E, et al. Accelerating eye movement research via accurate and affordable smartphone eye tracking. Nat Commun. 2020;11:4553. [44]
    4. Arakawa R, Goel M, Harrison C, Ahuja K. RGBDGaze: Gaze Tracking on Smartphones with RGB and Depth Data. Proc ICMI. 2022. doi:10.1145/3536221.3556568. [25]
    5. Gunawardena N, Lui GY, Ginige JA, Javadi B. Smartphone-based Eye Tracking System using Edge Intelligence and Model Optimisation. Internet of Things (Elsevier). 2024/2025; arXiv:2408.12463. [71]
    6. Khamis M, Alt F, Bulling A. The Past, Present, and Future of Gaze-enabled Handheld Mobile Devices: Survey and Lessons Learned. Proc MobileHCI. 2018. doi:10.1145/3229434.3229452. [7]
    7. Cheng Y, Wang H, Bao Y, Lu F. Appearance-based Gaze Estimation With Deep Learning: A Review and Benchmark. arXiv:2104.12668. [72]
    8. Kar A, Corcoran P. A Review and Analysis of Eye-Gaze Estimation Systems, Algorithms and Performance Evaluation Methods in Consumer Platforms. IEEE Access. 2017. doi:10.1109/ACCESS.2017.2735633. [52]
    9. Salvucci DD, Goldberg JH. Identifying Fixations and Saccades in Eye-Tracking Protocols. Proc ETRA. 2000. [54]
    10. Andersson R, Larsson L, Holmqvist K, Stridh M, Nyström M. One algorithm to rule them all? An evaluation and discussion of ten eye movement event detection algorithms. Behav Res Methods. 2016. doi:10.3758/s13428-016-0738-9. [73]
    11. Engbert R, Kliegl R. Microsaccades uncover the orientation of covert attention. Vision Res. 2003. [58]
    12. Cheng S, Ping Q, Wang J, Chen Y. EasyGaze: Hybrid eye tracking approach for handheld mobile devices. Virtual Reality & Intelligent Hardware. 2022. doi:10.1016/j.vrih.2021.10.003. [18]
    13. Lei Y, Zhao M, Wang Y, et al. MAC-Gaze: Motion-Aware Continual Calibration for Mobile Gaze Tracking. arXiv:2505.22769. [27]
    14. Apple. ARFaceAnchor.lookAtPoint – Apple Developer Documentation. [50]
    15. Tobii. Why choose Tobii Pro Glasses 3 for eye tracking research (PCCR; do 100 Hz). [74]
    16. Tobii. Tobii Pro SDK / Developer documentation (data streams). [75]
    17. Pupil Labs. Pupil Core (open platform) oraz repozytorium Pupil (open source). [76]
    18. Pupil Labs / Zenodo. Neon Accuracy Test Report. [77]
    19. Zhang X, Sugano Y, Bulling A. Evaluation of Appearance-Based Methods and Implications for Gaze-Based Applications (OpenGaze). arXiv:1901.10906. [78]
    20. Huang J, White R, Buscher G. WebGazer: Scalable Webcam Eye Tracking Using User Interactions. IJCAI. 2016. [34]
    21. TensorFlow. Post-training quantization (TFLite) – TensorFlow Model Optimization. [79]
    22. TensorFlow. Pruning in Keras example – TensorFlow Model Optimization. [80]
    23. Gou J, Yu B, Maybank SJ, Tao D. Knowledge Distillation: A Survey. Int J Comput Vis. 2021. [81]
    24. (Patent) US20160282937A1. Gaze tracking for a mobile device. Google Patents. [28]

[1] [8] [12] [36] [42] [43] [70] https://openaccess.thecvf.com/content_cvpr_2016/papers/Krafka_Eye_Tracking_for_CVPR_2016_paper.pdf
https://openaccess.thecvf.com/content_cvpr_2016/papers/Krafka_Eye_Tracking_for_CVPR_2016_paper.pdf
[2] [6] [14] [15] [20] [41] [46] [47] [48] [51] [64] [68] [71] https://ar5iv.org/pdf/2408.12463
https://ar5iv.org/pdf/2408.12463
[3] [72] https://arxiv.org/pdf/2104.12668v1
https://arxiv.org/pdf/2104.12668v1
[4] [5] [13] [17] [24] [26] [39] [69] https://www.mkhamis.com/data/papers/lei2023compsurveys.pdf
https://www.mkhamis.com/data/papers/lei2023compsurveys.pdf
[7] [9] [53] https://mkhamis.com/data/papers/khamis2018mobilehci.pdf
https://mkhamis.com/data/papers/khamis2018mobilehci.pdf
[10] [11] [16] [52] https://arxiv.org/pdf/1708.01817
https://arxiv.org/pdf/1708.01817
[18] [19] https://www.sciencedirect.com/science/article/pii/S2096579622000146
https://www.sciencedirect.com/science/article/pii/S2096579622000146
[21] [79] https://www.tensorflow.org/model_optimization/guide/quantization/post_training
https://www.tensorflow.org/model_optimization/guide/quantization/post_training
[22] [81] https://link.springer.com/article/10.1007/s11263-021-01453-z
https://link.springer.com/article/10.1007/s11263-021-01453-z
[23] https://arxiv.org/html/2604.02509v1
https://arxiv.org/html/2604.02509v1
[25] [66] [67] https://mynkgoel.github.io/pdfs/rgbd_gaze.pdf
https://mynkgoel.github.io/pdfs/rgbd_gaze.pdf
[27] [49] https://arxiv.org/abs/2505.22769
https://arxiv.org/abs/2505.22769
[28] https://patents.google.com/patent/US20160282937A1/en
https://patents.google.com/patent/US20160282937A1/en
[29] [32] [74] https://www.tobii.com/resource-center/learn-articles/why-choose-tobii-pro-glasses-3-for-eye-tracking-research
https://www.tobii.com/resource-center/learn-articles/why-choose-tobii-pro-glasses-3-for-eye-tracking-research
[30] https://developer.tobii.com/tobii-pro-sdk/
https://developer.tobii.com/tobii-pro-sdk/
[31] https://github.com/pupil-labs/pupil
https://github.com/pupil-labs/pupil
[33] https://git.hcics.simtech.uni-stuttgart.de/public-projects/opengaze
https://git.hcics.simtech.uni-stuttgart.de/public-projects/opengaze
[34] https://jeffhuang.com/papers/WebGazer_IJCAI16.pdf
https://jeffhuang.com/papers/WebGazer_IJCAI16.pdf
[35] https://github.com/swook/GazeML
https://github.com/swook/GazeML
[37] https://github.com/FIGLAB/RGBDGaze
https://github.com/FIGLAB/RGBDGaze
[38] https://colab.ws/articles/10.1016%2Fj.iot.2024.101481
https://colab.ws/articles/10.1016%2Fj.iot.2024.101481
[40] [44] [45] [59] [63] [65] https://www.nature.com/articles/s41467-020-18360-5
https://www.nature.com/articles/s41467-020-18360-5
[50] https://developer.apple.com/documentation/arkit/arfaceanchor/lookatpoint
https://developer.apple.com/documentation/arkit/arfaceanchor/lookatpoint
[54] [55] https://www.cs.drexel.edu/~salvucci/publications/Salvucci-ETRA00.pdf
https://www.cs.drexel.edu/~salvucci/publications/Salvucci-ETRA00.pdf
[56] [61] https://edepot.wur.nl/694170
https://edepot.wur.nl/694170
[57] https://arxiv.org/pdf/2512.23926
https://arxiv.org/pdf/2512.23926
[58] https://www.sciencedirect.com/science/article/pii/S0042698903000841
https://www.sciencedirect.com/science/article/pii/S0042698903000841
[60] [62] [73] https://link.springer.com/content/pdf/10.3758/s13428-016-0738-9.pdf
https://link.springer.com/content/pdf/10.3758/s13428-016-0738-9.pdf
[75] https://developer.tobiipro.com/
https://developer.tobiipro.com/
[76] https://pupil-labs.com/products/core
https://pupil-labs.com/products/core
[77] https://zenodo.org/records/10420388/files/neon-accuracy-test-report-v1-dec-2023.pdf?download=1
https://zenodo.org/records/10420388/files/neon-accuracy-test-report-v1-dec-2023.pdf?download=1
[78] https://arxiv.org/abs/1901.10906
https://arxiv.org/abs/1901.10906
[80] https://www.tensorflow.org/model_optimization/guide/pruning/pruning_with_keras
https://www.tensorflow.org/model_optimization/guide/pruning/pruning_with_keras

Eye tracking na urządzeniach mobilnych rozwija się obecnie w kilku głównych kierunkach. Pierwszy obejmuje rozwiązania oparte na zewnętrznej aparaturze, takiej jak screen-based eye trackery oraz okulary eye-trackingowe. Zapewniają one zwykle wysoką precyzję dzięki wykorzystaniu podczerwieni i detekcji odbicia rogówkowego, ale ich zastosowanie w warunkach codziennych jest ograniczane przez koszt, konieczność kalibracji oraz mniejszą mobilność użytkownika. Drugi, obecnie dominujący kierunek, wykorzystuje wbudowane kamery urządzeń mobilnych, przede wszystkim kamerę przednią, co pozwala realizować estymację spojrzenia bez dodatkowego sprzętu, lecz kosztem większej wrażliwości na oświetlenie, zmiany pozycji głowy, częściową widoczność twarzy i ograniczenia obliczeniowe smartfonów. 
Współczesne mobilne systemy eye trackingowe są najczęściej systemami appearance-based, w których estymacja punktu spojrzenia lub kierunku spojrzenia jest realizowana na podstawie obrazów twarzy i oczu, z użyciem modeli uczenia maszynowego, zwłaszcza głębokich sieci neuronowych. Typowy pipeline obejmuje etap wstępnego przetwarzania i detekcji twarzy, ekstrakcję cech z obrazu oczu, twarzy lub obu tych obszarów, właściwy model estymacji oraz kalibrację dopasowującą system do użytkownika, urządzenia i kontekstu użycia. W urządzeniach mobilnych wykorzystywane są obecnie nie tylko kamery RGB, ale także RGB-D i NIR/IR, które mogą poprawiać modelowanie geometrii głowy i oka oraz zwiększać odporność systemu na zakłócenia środowiskowe. 
Istotnym nurtem rozwoju są modele głębokie dostosowane do warunków mobilnych. W literaturze opisano architektury CNN operujące na obrazie twarzy, oczu i siatki twarzy, a także rozwiązania łączące CNN z modelami sekwencyjnymi, takimi jak LSTM i GRU, aby lepiej uchwycić dynamikę spojrzenia w czasie. Szczególnie ważne jest to dla treści dynamicznych, takich jak wideo, gry, AR i VR, gdzie klasyczne modele trenowane na statycznych bodźcach osiągają gorsze wyniki. W badaniu dotyczącym smartfonowego eye trackingu dla bodźców dynamicznych model CNN+LSTM osiągnął błąd RMSE 0,955 cm, a CNN+GRU 1,091 cm, co wskazuje, że modelowanie zależności czasowych może poprawiać dokładność w zastosowaniach mobilnych. 
Równolegle rośnie znaczenie rozwiązań brzegowych i optymalizacji modeli. Ze względu na ograniczoną moc obliczeniową, pamięć i baterię smartfonów, część przetwarzania może być przenoszona na urządzenia edge, co zmniejsza opóźnienia względem chmury i poprawia możliwość działania w czasie rzeczywistym. Dodatkowo stosuje się techniki takie jak pruning i quantisation, które redukują koszt inferencji i zużycie zasobów, choć zwykle kosztem częściowej utraty dokładności. Z tego względu współczesne mobilne systemy eye trackingowe należy traktować jako kompromis między precyzją, odpornością na warunki naturalne, wymaganiami kalibracyjnymi oraz efektywnością obliczeniową. 

Literatura
    [1]  Gunawardena, N., Ginige, J. A., Javadi, B. Eye-tracking Technologies in Mobile Devices Using Edge Computing: A Systematic Review. ACM Computing Surveys, 55(8), 2022. DOI: 10.1145/3546938. 
    [2]  Lei, Y., He, S., Khamis, M., Ye, J. An End-to-End Review of Gaze Estimation and its Interactive Applications on Handheld Mobile Devices. ACM Computing Surveys, 56(2), 2023. DOI: 10.1145/3606947. 
    [3]  Gunawardena, N., Lui, G. Y., Ginige, J. A., Javadi, B. Smartphone-based eye tracking system using edge intelligence and model optimisation. Internet of Things, 29, 101481, 2025. DOI: 10.1016/j.iot.2024.101481. 
    [4]  Goldberg, H., Christensen, A., Flash, T., Giese, M. A., Malach, R. Brain activity correlates with emotional perception induced by dynamic avatars. NeuroImage, 122, 306–317, 2015. DOI: 10.1016/j.neuroimage.2015.07.056.

