# Template Helper

## Styles
Common Styles can be found in
```html
<link rel="stylesheet" href="Styles.css">
```

Those will be required for the Common Blocks

## Common Blocks

### Header

```html
    <div class="HeaderLine">
        <address>
            Studierendenschaft der Hochschule Esslingen<br>
            Allgemeiner Studierendenausschuss<br>
            Kanalstr. 33<br>
            73728 Esslingen<br>
            <br>
            E-Mail: asta@hs-esslingen.de
        </address>

        <img src="AstaRgb.png">
    </div>
```

### General Information
```html
 <div class="GeneralInformationBox">
    <p class="pgeneral"><strong>Kurzbezeichnung des Antrages:</strong> <input class="FirstPageGeneral" type="text" th:value="${bez}"></p>
    <p class="pgeneral"><strong>Fachschaft/Referat/Arbeitskreis:</strong> <input class="FirstPageGeneral" type="text" th:value="${fs_ref_ar}"></p>
    <p class="pgeneral"><strong>Kontaktdaten </strong>(Handy-Nr. E-mail): <input class="FirstPageGeneral" type="text" th:value="${contact}"></p>
    <p class="pgeneral"><strong>Antragsteller*in:</strong> (Name Vorname) <input class="FirstPageGeneral" type="text" th:value="${user}"></p>
  </div>
```

### Signature Line
```html
<div class="signature">
    <div>
      <p>_____________________________________</p>
      <p>Datum/Unterschrift AStA-Vorsitz</p>
    </div>
    <div>
      <p>_____________________________________</p>
      <p>Datum/Unterschrift Finanzreferat</p>
    </div>
  </div>
```

### Payment Receiver Block
```html
  <div class="ReciverBlock">
    <p class="vorschussReciver" ><strong>Empfänger*in:</strong><input class="vorschussReciver alignRight vorschussFullLen" type="text"></p>
    <p class="vorschussReciver" ><strong>Anschrift:</strong><input class="vorschussReciver alignRight vorschussFullLen" type="text"></p>

    <p  class="vorschussReciver" ><strong>IBAN:</strong> (22 Stellen)<input class="ibanfirst ibanwide vorschussReciver" type="text"><input class="ibanwide vorschussReciver" type="text"><input class="ibanwide vorschussReciver" type="text"><input class="ibanwide vorschussReciver" type="text"><input class="ibanwide vorschussReciver" type="text"><input class="ibanslim vorschussReciver" type="text"></p>
    <p class="vorschussReciver" ><strong>Kreditinstitut:</strong><input class="vorschussReciver alignRight vorschussFullLen" type="text"></p>
  </div>
```

### Unterschrift Antrags Steller
```html
<input type="text" class="UnterschriftSteller">
  <p><strong>Datum/Unterschrift Antragsteller*in:</strong></p>
  <p class="ichBestaetige Italic">* Ich bestätige die Vollständigkeit und Richtigkeit der oben gemachten Angaben.</p>
```

### Unordered list
```html
    <ul>
        <li><strong class="strongsizeSmaller"></strong></li>
        <li><strong class="strongsizeSmaller"></strong></li>
    </ul>
```