# Do kraja — native Android widget

Kompletan, samostalan Android projekt. Kompajlira se automatski na GitHubu
(GitHub Actions) — ne treba ti Android Studio.

## Kako dobiti APK

1. Na GitHubu napravi NOVI repozitorij (npr. `do-kraja-widget`), javni ili privatni.
2. Uploadaj SAV sadržaj ovog zipa u root repozitorija, zadržavajući strukturu
   foldera (najlakše: otpakiraj zip, pa na GitHubu "Add file → Upload files"
   i povuci CIJELI otpakirani folder u prozor — GitHub sam čuva podfoldere).
   Pazi da `.github/workflows/build.yml` završi na tom mjestu (folder `.github`
   je skriven na računalu — provjeri da se ipak uploadao).
3. Commit. GitHub Actions se automatski pokreće (tab "Actions" u repozitoriju).
   Prvi build traje ~5-8 min.
4. Kad je zeleno: otvori taj run → dolje "Artifacts" → preuzmi
   `do-kraja-widget-apk` (zip u kojem je `app-debug.apk`).
5. Prebaci `app-debug.apk` na telefon, otvori ga, dopusti instalaciju iz
   nepoznatog izvora, instaliraj.
6. Dugi pritisak na home screen → Widgeti → "Do kraja" → dodaj. Otvori se
   ekran postavki (datum, boja: Sustav / Presetovi / Iz slike) → Spremi.

## Ako build padne

Otvori crveni run → klikni job "build" → kopiraj crveni dio loga i pošalji mi.
Najčešći uzrok su verzije alata (AGP/Kotlin/Compose) — to se popravlja u
`build.gradle.kts` datotekama, ne u samom kodu widgeta.

## Promjena zadnjeg dana nastave / boje kasnije

Dugi pritisak na sam widget → "Uredi" otvara isti ekran postavki.
