# Plan realizacji: arc-mobile-eyetracking

**Wersja dokumentu: 1.0.0. Data: 2026-10-13.**
**Status: plan prac, zadania poniżej nie są oznaczone jako wykonane.**

Plan jest rozwinięciem aktualnego PROJECT_STATE.yaml i istniejących protokołów. Nie zmienia hipotez, kryteriów, zamrożonych metod ani statusu zgłoszeń. Źródła naukowe oraz dowody pozostają w dotychczasowych rejestrach. Przed następnym wykonaniem odczytaj bieżący main i sprawdź zmiany wobec tego snapshotu.

**Przeanalizowany main SHA:** `6cd1171d4d71c0256b4d9ed2c3cb64a47fe22e69`

**Stan odczytany ze źródeł:** Kontrakt pomiarowy i UI istnieją. Kamera, estymator, kalibracja oraz eksport są opisane jako niezaimplementowane; badań uczestników jeszcze nie zebrano.

**Pierwszy pakiet:** Zaimplementować najmniejszy kompletny przepływ pomiaru za istniejącym typed contract

## Źródła i istniejące instrukcje

- [AGENTS.md](https://github.com/MatPomGit/arc-mobile-eyetracking/blob/6cd1171d4d71c0256b4d9ed2c3cb64a47fe22e69/AGENTS.md)
- [PROJECT_STATE.yaml](https://github.com/MatPomGit/arc-mobile-eyetracking/blob/6cd1171d4d71c0256b4d9ed2c3cb64a47fe22e69/PROJECT_STATE.yaml)
- [README.md](https://github.com/MatPomGit/arc-mobile-eyetracking/blob/6cd1171d4d71c0256b4d9ed2c3cb64a47fe22e69/README.md)
- [docs/measurement_contract.md](https://github.com/MatPomGit/arc-mobile-eyetracking/blob/6cd1171d4d71c0256b4d9ed2c3cb64a47fe22e69/docs/measurement_contract.md)
- [docs/research_plan.md](https://github.com/MatPomGit/arc-mobile-eyetracking/blob/6cd1171d4d71c0256b4d9ed2c3cb64a47fe22e69/docs/research_plan.md)
- [research/quality_manifest.yaml](https://github.com/MatPomGit/arc-mobile-eyetracking/blob/6cd1171d4d71c0256b4d9ed2c3cb64a47fe22e69/research/quality_manifest.yaml)

## Kolejność zadań

Zadania bez zależności można przygotowywać niezależnie. Właściwe badanie wymaga spełnienia wszystkich bramek jego istniejącego protokołu. AI przygotowuje materiały i analizy; operator wykonuje pomiary urządzeń, a zespół badawczy badania uczestników.

| ID | Zadanie | Wykonawca | Poprzedniki / warunki | Rezultat | Kryterium ukończenia |
| --- | --- | --- | --- | --- | --- |
| E01 | Zaimplementować kamerę i timestampy za istniejącym kontraktem; dodać wersjonowany adapter estymatora | AI | brak | Implementacja app i fixtures | Invalid/low-quality jawne; nie tworzyć sztucznych współrzędnych |
| E02 | Dodać kalibrację, transformacje układów, stany jakości i wersjonowany eksport | AI | E01 | Pipeline pomiarowy i testy deterministyczne | Brak domyślnego zapisu raw video; geometria, jednostki i wznowienie zweryfikowane |
| E03 | Zamrozić E01 target geometry, kolejność, kalibrację, warunki, metryki i stopping rule | AI + kierownik badania | E02 | Protokół E01 i ścieżka zgód | Calibration targets oddzielone od held-out; metryki błędu/precyzji i awarii ustalone |
| E04 | Wykonać controlled human target validation bez strojenia na held-out | zespół badawczy | E03 + właściwe zgody | Pakiet E01 i generated metrics | Zachowano invalid samples i rozkłady per osoba/per device |
| E05 | Wykonać prespecified robustness i cross-device E02/E03 | operator + zespół | E04 + właściwe freeze | Pakiety perturbacji/urządzeń | Odległość, head pose i illumination z góry ustalone |
| E06 | Jeśli sprzęt referencyjny dostępny, zamrozić i wykonać osobny E04 synchronizacji/zgodności | operator + AI | E04,E05 + dostęp referencji | Pakiet external-reference comparison | Internal screen targets nie są dowodem równoważności laboratoryjnej |
| E07 | Przygotować P01 z odtwarzalnych błędów, missingness i granic zastosowania | AI + autor | E04,E05; E06 tylko dla jego twierdzeń | Claim ledger i PDF P01 | Wszystkie liczby z artefaktów analizy; brak niezweryfikowanej dokładności |

## Ścieżki publikacyjne

To istniejące kierunki programu, w tym kandydaci warunkowi. Ich liczba nie jest obietnicą liczby publikacji. Zakres artykułu wynika z rzeczywistych dowodów.

| Manuskrypt | Kroki | Bramka / ograniczenie interpretacji |
| --- | --- | --- |
| P01 | Implementacja → software qualification → held-out targets → robustness/devices → opcjonalna referencja → artykuł | Walidacja instrumentu, nie tylko Android build |

## Zapis wyniku każdego zadania

1. Zweryfikuj bieżący SHA, AGENTS.md, PROJECT_STATE.yaml i właściwy protokół
2. Wykonaj jeden zakres prac z powyższej tabeli i zapisuj wyniki w jego istniejącej lokalizacji
3. Uruchom właściwe testy lub analizę; zapisz dokładne polecenie, wersję, wynik i ograniczenia
4. Zarejestruj rzeczywisty artefakt i jego sumę; zaktualizuj istniejący TODO, evidence/claim ledger oraz PROJECT_STATE.yaml tylko w zakresie faktycznej zmiany
5. Zamknij zadanie dopiero po spełnieniu kryterium; jeśli jest zablokowane, zapisz brak i przejdź do zadania bez tej zależności

Dodatkowy koszt usług: 0. Ciężkie obliczenia wymagają zweryfikowanej gotowości lokalnego sprzętu. Andromeda nie jest uruchamiana automatycznie. Nie przechowuj danych uczestników, sekretów ani licencjonowanych pełnych tekstów w Git.

## Weryfikacja tego planu

Użyto funkcji analyse_paths z research_repo_audit.py oraz compile_status z research_portfolio_status.py projektu research-autopilot. Odczytano przypięte źródła, zweryfikowano ich Git blob SHA i kompletność drzewa plików. Kontrola struktury nie ocenia jakości naukowej i nie zastępuje kontroli pełnych tekstów, danych ani metod.

Przed dopisaniem do tego dokumentu zwiększ numer wersji zgodnie ze schematem 1.x.x. Nie zmieniaj historycznego SHA tak, aby wyglądał na nowy audyt.
