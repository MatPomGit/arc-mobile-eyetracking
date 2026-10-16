# Audyt portfela artykułów — 2026-10-16

Źródło: aktualny README i lista otwartych issues; przegląd zarządczy, bez uruchomienia badań lub kompletnej oceny tekstów manuskryptów.

## Portfel
P01, E00–E04

## Stan udokumentowany
README deklaruje szkielet UI bez kompletnego pipeline camera→landmarks→gaze→calibration→quality→export. Istnieje plan kwalifikacji E00 oraz późniejszej walidacji E01–E03; brak uprawnienia do deklarowania dokładności pomiarów.

## Pozostałe bramki
E00 testy negatywne, invariants, replay i provenance; E01–E03 wymagają urządzeń i pomiarów; E04 zewnętrzny eye tracker warunkowy.

## Zasady raportowania
- Rozróżnić stopień przygotowania tekstu, narzędzi, danych, walidacji, zgód i status publikacyjny.
- Nie wyliczać procentu naukowej gotowości bez przypisanej, weryfikowalnej rubryki.
- Dane syntetyczne nie są wynikami z uczestników, a wykonanie CI nie poświadcza skuteczności ani bezpieczeństwa.
- W istniejących issues stosować pola `work_types`, `execution_mode`, `human_approval_required`, `evidence_paths` i `acceptance_criteria`, z dokładnymi zależnościami i bramkami.
- Autonomia AI: analiza kodu, dokumentacji, testy syntetyczne i audyt cytowań; człowiek: etyka, rekrutacja, fizyczne pomiary, decyzje publikacyjne i zatwierdzenie dowodów.
