# Procedura badań i pracy agentów

**Wersja:** 1.0.0 (2026-10-31). Uzupełnienie [AGENTS.md](../../AGENTS.md). Nie upoważnia do badań z ludźmi ani używania prywatnego wideo.

## Wybór zadania
Przeczytaj PROJECT_STATE.yaml, research/quality_manifest.yaml, docs/research_plan.md i docs/measurement_contract.md oraz aktualne main, Issues, milestones, PR i CI. Dokończ istniejące PR przed nowym feature. DoR: cel, mierzalny wynik, zależności, uprawnienia, bezpieczeństwo danych i kryteria akceptacji. Rezerwuj Issue z istniejącym Research Autopilot (jedyny kontroler); nie twórz drugiej kolejki. Limit jeden aktywny feature PR na repozytorium i pięć w portfolio. Skille wybieraj po sprawdzeniu wersji, pochodzenia, dostępności i minimalnych uprawnień.

## Kod i granice nauki
Android Kotlin Compose-first, Material 3, ViewModel/StateFlow i obowiązujące kontrakty. Nie zastępuj działających widoków tylko dla zmiany technologii. KISS, DRY, YAGNI i brak zbędnych pakietów. UI scaffold, build Androida lub kamera nie są dowodem walidacji gaze. Syntetyczny sygnał, kontrolowany test targetów i walidacja względem zewnętrznego eyetrackera są różnymi klasami dowodów. Missing/invalid gaze nie może być zastąpiony fikcyjną współrzędną. Kalibracja nie używa końcowych targetów oceny. Nie przechowuj surowego filmu twarzy/pomieszczenia bez prawa, zgody i właściwego protokołu.

Dokumentuj sensor, urządzenie, wersje, dane, seedy, warunki pomiaru, rozdział uczestników/targetów, niepewność i negatywne wyniki. Nie fabrykuj kalibracji, trafności, aprobacji etycznych, publikacji i cytowań. Pythona stosuj wg właściwych PEP 8/257/484, nie dodawaj lockfile lub pakietów na zapas.

## Testy i integracja
Dla każdego testu podaj dokładny SHA, komendę, środowisko i wynik PASS/FAIL/BLOCKED/UNAVAILABLE/NOT_RUN. Odróżniaj JVM unit tests, emulator, fizyczny telefon i dowody empiryczne. Nie uruchamiaj GitHub Actions na feature PR, jeśli polityka wymaga main/manual-first. Nie używaj płatnych usług ani niezaufanego kodu na trwałym runnerze. Przed merge sprawdź obowiązujące branch protections, CI i niezależne review; komentarz tego samego konta nie stanowi formalnego approval. Rewaliduj zmieniony HEAD.

DoD: kryteria, dokumenty i Issue, zweryfikowane testy, scalony autoryzowany PR, odrębne potwierdzenie wdrożenia (jeśli dotyczy), bezpieczne usunięcie własnej scalonej gałęzi/worktree. Zapisz blocker, exit condition i kolejny JUMP. W dokumentacji użytkowej stosuj poprawną polszczyznę bez U+2013/U+2014 poza niezmiennym cytatem. Materiały dydaktyczne: cztery wiarygodne dystraktory, oddzielny klucz.
