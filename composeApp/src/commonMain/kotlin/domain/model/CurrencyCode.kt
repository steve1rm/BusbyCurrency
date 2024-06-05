package domain.model

import busbycurrency.composeapp.generated.resources.Res
import busbycurrency.composeapp.generated.resources.afghanistan
import busbycurrency.composeapp.generated.resources.albania
import busbycurrency.composeapp.generated.resources.algeria
import busbycurrency.composeapp.generated.resources.angola
import busbycurrency.composeapp.generated.resources.argentina
import busbycurrency.composeapp.generated.resources.armenia
import busbycurrency.composeapp.generated.resources.australia
import busbycurrency.composeapp.generated.resources.azerbaijan
import busbycurrency.composeapp.generated.resources.bahamas
import busbycurrency.composeapp.generated.resources.bahrain
import busbycurrency.composeapp.generated.resources.bangladesh
import busbycurrency.composeapp.generated.resources.barbados
import busbycurrency.composeapp.generated.resources.belarus
import busbycurrency.composeapp.generated.resources.belize
import busbycurrency.composeapp.generated.resources.bermuda
import busbycurrency.composeapp.generated.resources.bhutan
import busbycurrency.composeapp.generated.resources.bolivia
import busbycurrency.composeapp.generated.resources.bosnia_and_herzegovina
import busbycurrency.composeapp.generated.resources.botswana
import busbycurrency.composeapp.generated.resources.brazil
import busbycurrency.composeapp.generated.resources.bulgaria
import busbycurrency.composeapp.generated.resources.cambodia
import busbycurrency.composeapp.generated.resources.canada
import busbycurrency.composeapp.generated.resources.cape_verde
import busbycurrency.composeapp.generated.resources.central_african_republic
import busbycurrency.composeapp.generated.resources.chile
import busbycurrency.composeapp.generated.resources.china
import busbycurrency.composeapp.generated.resources.colombia
import busbycurrency.composeapp.generated.resources.comoros
import busbycurrency.composeapp.generated.resources.costa_rica
import busbycurrency.composeapp.generated.resources.croatia
import busbycurrency.composeapp.generated.resources.cuba
import busbycurrency.composeapp.generated.resources.czech_republic
import busbycurrency.composeapp.generated.resources.democratic_republic_of_congo
import busbycurrency.composeapp.generated.resources.denmark
import busbycurrency.composeapp.generated.resources.djibouti
import busbycurrency.composeapp.generated.resources.dominican_republic
import busbycurrency.composeapp.generated.resources.egypt
import busbycurrency.composeapp.generated.resources.el_salvador
import busbycurrency.composeapp.generated.resources.eritrea
import busbycurrency.composeapp.generated.resources.ethiopia
import busbycurrency.composeapp.generated.resources.european_union
import busbycurrency.composeapp.generated.resources.falkland_islands
import busbycurrency.composeapp.generated.resources.fiji
import busbycurrency.composeapp.generated.resources.gambia
import busbycurrency.composeapp.generated.resources.georgia
import busbycurrency.composeapp.generated.resources.ghana
import busbycurrency.composeapp.generated.resources.gibraltar
import busbycurrency.composeapp.generated.resources.guatemala
import busbycurrency.composeapp.generated.resources.guernsey
import busbycurrency.composeapp.generated.resources.guinea
import busbycurrency.composeapp.generated.resources.guyana
import busbycurrency.composeapp.generated.resources.haiti
import busbycurrency.composeapp.generated.resources.honduras
import busbycurrency.composeapp.generated.resources.hong_kong
import busbycurrency.composeapp.generated.resources.hungary
import busbycurrency.composeapp.generated.resources.iceland
import busbycurrency.composeapp.generated.resources.india
import busbycurrency.composeapp.generated.resources.indonesia
import busbycurrency.composeapp.generated.resources.iran
import busbycurrency.composeapp.generated.resources.iraq
import busbycurrency.composeapp.generated.resources.isle_of_man
import busbycurrency.composeapp.generated.resources.israel
import busbycurrency.composeapp.generated.resources.jamaica
import busbycurrency.composeapp.generated.resources.japan
import busbycurrency.composeapp.generated.resources.jersey
import busbycurrency.composeapp.generated.resources.jordan
import busbycurrency.composeapp.generated.resources.kazakhstan
import busbycurrency.composeapp.generated.resources.kenya
import busbycurrency.composeapp.generated.resources.kuwait
import busbycurrency.composeapp.generated.resources.kyrgyzstan
import busbycurrency.composeapp.generated.resources.laos
import busbycurrency.composeapp.generated.resources.lebanon
import busbycurrency.composeapp.generated.resources.lesotho
import busbycurrency.composeapp.generated.resources.liberia
import busbycurrency.composeapp.generated.resources.libya
import busbycurrency.composeapp.generated.resources.lithuania
import busbycurrency.composeapp.generated.resources.macao
import busbycurrency.composeapp.generated.resources.madagascar
import busbycurrency.composeapp.generated.resources.malawi
import busbycurrency.composeapp.generated.resources.malaysia
import busbycurrency.composeapp.generated.resources.maldives
import busbycurrency.composeapp.generated.resources.mauritania
import busbycurrency.composeapp.generated.resources.mauritius
import busbycurrency.composeapp.generated.resources.mexico
import busbycurrency.composeapp.generated.resources.moldova
import busbycurrency.composeapp.generated.resources.mongolia
import busbycurrency.composeapp.generated.resources.morocco
import busbycurrency.composeapp.generated.resources.mozambique
import busbycurrency.composeapp.generated.resources.myanmar
import busbycurrency.composeapp.generated.resources.namibia
import busbycurrency.composeapp.generated.resources.nepal
import busbycurrency.composeapp.generated.resources.netherlands
import busbycurrency.composeapp.generated.resources.new_zealand
import busbycurrency.composeapp.generated.resources.nicaragua
import busbycurrency.composeapp.generated.resources.nigeria
import busbycurrency.composeapp.generated.resources.north_korea
import busbycurrency.composeapp.generated.resources.north_macedonia
import busbycurrency.composeapp.generated.resources.norway
import busbycurrency.composeapp.generated.resources.oman
import busbycurrency.composeapp.generated.resources.pakistan
import busbycurrency.composeapp.generated.resources.panama
import busbycurrency.composeapp.generated.resources.papua_new_guinea
import busbycurrency.composeapp.generated.resources.paraguay
import busbycurrency.composeapp.generated.resources.philippines
import busbycurrency.composeapp.generated.resources.poland
import busbycurrency.composeapp.generated.resources.qatar
import busbycurrency.composeapp.generated.resources.romania
import busbycurrency.composeapp.generated.resources.russia
import busbycurrency.composeapp.generated.resources.rwanda
import busbycurrency.composeapp.generated.resources.samoa
import busbycurrency.composeapp.generated.resources.sao_tome_and_prince
import busbycurrency.composeapp.generated.resources.saudi_arabia
import busbycurrency.composeapp.generated.resources.serbia
import busbycurrency.composeapp.generated.resources.seychelles
import busbycurrency.composeapp.generated.resources.sierra_leone
import busbycurrency.composeapp.generated.resources.singapore
import busbycurrency.composeapp.generated.resources.solomon_islands
import busbycurrency.composeapp.generated.resources.somalia
import busbycurrency.composeapp.generated.resources.south_africa
import busbycurrency.composeapp.generated.resources.south_korea
import busbycurrency.composeapp.generated.resources.sri_lanka
import busbycurrency.composeapp.generated.resources.sudan
import busbycurrency.composeapp.generated.resources.suriname
import busbycurrency.composeapp.generated.resources.sweden
import busbycurrency.composeapp.generated.resources.switzerland
import busbycurrency.composeapp.generated.resources.syria
import busbycurrency.composeapp.generated.resources.taiwan
import busbycurrency.composeapp.generated.resources.tajikistan
import busbycurrency.composeapp.generated.resources.tanzania
import busbycurrency.composeapp.generated.resources.thailand
import busbycurrency.composeapp.generated.resources.tonga
import busbycurrency.composeapp.generated.resources.trinidad_and_tobago
import busbycurrency.composeapp.generated.resources.tunisia
import busbycurrency.composeapp.generated.resources.turkey
import busbycurrency.composeapp.generated.resources.turkmenistan
import busbycurrency.composeapp.generated.resources.ukraine
import busbycurrency.composeapp.generated.resources.united_kingdom
import busbycurrency.composeapp.generated.resources.united_states
import busbycurrency.composeapp.generated.resources.uzbekista_n
import busbycurrency.composeapp.generated.resources.vanuatu
import busbycurrency.composeapp.generated.resources.venezuela
import busbycurrency.composeapp.generated.resources.vietnam
import busbycurrency.composeapp.generated.resources.yemen
import busbycurrency.composeapp.generated.resources.zambia
import busbycurrency.composeapp.generated.resources.zimbabwe
import org.jetbrains.compose.resources.DrawableResource

enum class CurrencyCode(
    val country: String,
    val flag: DrawableResource
) {
    AFN(country = "Afghanistan", flag = Res.drawable.afghanistan),
    ALL(country = "Albania", flag = Res.drawable.albania),
    AMD(country = "Armenia", flag = Res.drawable.armenia),
    ANG(country = "Netherlands", flag = Res.drawable.netherlands),
    AOA(country = "Angola", flag = Res.drawable.angola),
    ARS(country = "Argentina", flag = Res.drawable.argentina),
    AUD(country = "Australia", flag = Res.drawable.australia),
    AZN(country = "Azerbaijan", flag = Res.drawable.azerbaijan),
    BAM(country = "Bosnia and Herzegovina", flag = Res.drawable.bosnia_and_herzegovina),
    BBD(country = "Barbados", flag = Res.drawable.barbados),
    BDT(country = "Bangladesh", flag = Res.drawable.bangladesh),
    BGN(country = "Bulgaria", flag = Res.drawable.bulgaria),
    BHD(country = "Bahrain", flag = Res.drawable.bahrain),
    BMD(country = "Bermuda", flag = Res.drawable.bermuda),
    BOB(country = "Bolivia", flag = Res.drawable.bolivia),
    BRL(country = "Brazil", flag = Res.drawable.brazil),
    BSD(country = "Bahamas", flag = Res.drawable.bahamas),
    BTN(country = "Bhutan", flag = Res.drawable.bhutan),
    BWP(country = "Botswana", flag = Res.drawable.botswana),
    BYN(country = "Belarus", flag = Res.drawable.belarus),
    BZD(country = "Belize", flag = Res.drawable.belize),
    CAD(country = "Canada", flag = Res.drawable.canada),
    CDF(country = "Democratic Republic of Congo", flag = Res.drawable.democratic_republic_of_congo),
    CHF(country = "Switzerland", flag = Res.drawable.switzerland),
    CLF(country = "Chile (Unidad de Fomento)", flag = Res.drawable.chile),
    CLP(country = "Chile", flag = Res.drawable.chile),
    CNY(country = "China", flag = Res.drawable.china),
    COP(country = "Colombia", flag = Res.drawable.colombia),
    CRC(country = "Costa Rica", flag = Res.drawable.costa_rica),
    CUC(country = "Cuba (Convertible Peso)", flag = Res.drawable.cuba),
    CUP(country = "Cuba (Cuban Peso)", flag = Res.drawable.cuba),
    CVE(country = "Cape Verde", flag = Res.drawable.cape_verde),
    CZK(country = "Czech Republic", flag = Res.drawable.czech_republic),
    DJF(country = "Djibouti", flag = Res.drawable.djibouti),
    DKK(country = "Denmark", flag = Res.drawable.denmark),
    DOP(country = "Dominican Republic", flag = Res.drawable.dominican_republic),
    DZD(country = "Algeria", flag = Res.drawable.algeria),
    EGP(country = "Egypt", flag = Res.drawable.egypt),
    ERN(country = "Eritrea", flag = Res.drawable.eritrea),
    ETB(country = "Ethiopia", flag = Res.drawable.ethiopia),
    EUR(country = "European Union", flag = Res.drawable.european_union),
    FJD(country = "Fiji", flag = Res.drawable.fiji),
    FKP(country = "Falkland Islands", flag = Res.drawable.falkland_islands),
    GBP(country = "United Kingdom", flag = Res.drawable.united_kingdom),
    GEL(country = "Georgia", flag = Res.drawable.georgia),
    GGP(country = "Guernsey", flag = Res.drawable.guernsey),
    GHS(country = "Ghana", flag = Res.drawable.ghana),
    GIP(country = "Gibraltar", flag = Res.drawable.gibraltar),
    GMD(country = "Gambia", flag = Res.drawable.gambia),
    GNF(country = "Guinea", flag = Res.drawable.guinea),
    GTQ(country = "Guatemala", flag = Res.drawable.guatemala),
    GYD(country = "Guyana", flag = Res.drawable.guyana),
    HKD(country = "Hong Kong", flag = Res.drawable.hong_kong),
    HNL(country = "Honduras", flag = Res.drawable.honduras),
    HRK(country = "Croatia", flag = Res.drawable.croatia),
    HTG(country = "Haiti", flag = Res.drawable.haiti),
    HUF(country = "Hungary", flag = Res.drawable.hungary),
    IDR(country = "Indonesia", flag = Res.drawable.indonesia),
    ILS(country = "Israel", flag = Res.drawable.israel),
    IMP(country = "Isle of Man", flag = Res.drawable.isle_of_man),
    INR(country = "India", flag = Res.drawable.india),
    IQD(country = "Iraq", flag = Res.drawable.iraq),
    IRR(country = "Iran", flag = Res.drawable.iran),
    ISK(country = "Iceland", flag = Res.drawable.iceland),
    JEP(country = "Jersey", flag = Res.drawable.jersey),
    JMD(country = "Jamaica", flag = Res.drawable.jamaica),
    JOD(country = "Jordan", flag = Res.drawable.jordan),
    JPY(country = "Japan", flag = Res.drawable.japan),
    KES(country = "Kenya", flag = Res.drawable.kenya),
    KGS(country = "Kyrgyzstan", flag = Res.drawable.kyrgyzstan),
    KHR(country = "Cambodia", flag = Res.drawable.cambodia),
    KMF(country = "Comoros", flag = Res.drawable.comoros),
    KPW(country = "North Korea", flag = Res.drawable.north_korea),
    KRW(country = "South Korea", flag = Res.drawable.south_korea),
    KWD(country = "Kuwait", flag = Res.drawable.kuwait),
    KZT(country = "Kazakhstan", flag = Res.drawable.kazakhstan),
    LAK(country = "Laos", flag = Res.drawable.laos),
    LBP(country = "Lebanon", flag = Res.drawable.lebanon),
    LKR(country = "Sri Lanka", flag = Res.drawable.sri_lanka),
    LRD(country = "Liberia", flag = Res.drawable.liberia),
    LSL(country = "Lesotho", flag = Res.drawable.lesotho),
    LTL(country = "Lithuania", flag = Res.drawable.lithuania),
    LYD(country = "Libya", flag = Res.drawable.libya),
    MAD(country = "Morocco", flag = Res.drawable.morocco),
    MDL(country = "Moldova", flag = Res.drawable.moldova),
    MGA(country = "Madagascar", flag = Res.drawable.madagascar),
    MKD(country = "North Macedonia", flag = Res.drawable.north_macedonia),
    MMK(country = "Myanmar", flag = Res.drawable.myanmar),
    MNT(country = "Mongolia", flag = Res.drawable.mongolia),
    MOP(country = "Macao", flag = Res.drawable.macao),
    MRO(country = "Mauritania", flag = Res.drawable.mauritania),
    MRU(country = "Mauritius", flag = Res.drawable.mauritius),
    MUR(country = "Mauritius", flag = Res.drawable.mauritius),
    MVR(country = "Maldives", flag = Res.drawable.maldives),
    MWK(country = "Malawi", flag = Res.drawable.malawi),
    MXN(country = "Mexico", flag = Res.drawable.mexico),
    MYR(country = "Malaysia", flag = Res.drawable.malaysia),
    MZN(country = "Mozambique", flag = Res.drawable.mozambique),
    NAD(country = "Namibia", flag = Res.drawable.namibia),
    NGN(country = "Nigeria", flag = Res.drawable.nigeria),
    NIO(country = "Nicaragua", flag = Res.drawable.nicaragua),
    NOK(country = "Norway", flag = Res.drawable.norway),
    NPR(country = "Nepal", flag = Res.drawable.nepal),
    NZD(country = "New Zealand", flag = Res.drawable.new_zealand),
    OMR(country = "Oman", flag = Res.drawable.oman),
    PAB(country = "Panama", flag = Res.drawable.panama),
    PGK(country = "Papua New Guinea", flag = Res.drawable.papua_new_guinea),
    PHP(country = "Philippines", flag = Res.drawable.philippines),
    PKR(country = "Pakistan", flag = Res.drawable.pakistan),
    PLN(country = "Poland", flag = Res.drawable.poland),
    PYG(country = "Paraguay", flag = Res.drawable.paraguay),
    QAR(country = "Qatar", flag = Res.drawable.qatar),
    RON(country = "Romania", flag = Res.drawable.romania),
    RSD(country = "Serbia", flag = Res.drawable.serbia),
    RUB(country = "Russia", flag = Res.drawable.russia),
    RWF(country = "Rwanda", flag = Res.drawable.rwanda),
    SAR(country = "Saudi Arabia", flag = Res.drawable.saudi_arabia),
    SBD(country = "Solomon Islands", flag = Res.drawable.solomon_islands),
    SCR(country = "Seychelles", flag = Res.drawable.seychelles),
    SDG(country = "Sudan", flag = Res.drawable.sudan),
    SEK(country = "Sweden", flag = Res.drawable.sweden),
    SGD(country = "Singapore", flag = Res.drawable.singapore),
    SLL(country = "Sierra Leone", flag = Res.drawable.sierra_leone),
    SOS(country = "Somalia", flag = Res.drawable.somalia),
    SRD(country = "Suriname", flag = Res.drawable.suriname),
    STD(country = "São Tomé and Príncipe", flag = Res.drawable.sao_tome_and_prince),
    STN(country = "São Tomé and Príncipe", flag = Res.drawable.sao_tome_and_prince),
    SVC(country = "El Salvador", flag = Res.drawable.el_salvador),
    SYP(country = "Syria", flag = Res.drawable.syria),
    THB(country = "Thailand", flag = Res.drawable.thailand),
    TJS(country = "Tajikistan", flag = Res.drawable.tajikistan),
    TMT(country = "Turkmenistan", flag = Res.drawable.turkmenistan),
    TND(country = "Tunisia", flag = Res.drawable.tunisia),
    TOP(country = "Tonga", flag = Res.drawable.tonga),
    TRY(country = "Turkiye", flag = Res.drawable.turkey),
    TTD(country = "Trinidad and Tobago", flag = Res.drawable.trinidad_and_tobago),
    TWD(country = "Taiwan", flag = Res.drawable.taiwan),
    TZS(country = "Tanzania", flag = Res.drawable.tanzania),
    UAH(country = "Ukraine", flag = Res.drawable.ukraine),
    USD(country = "United States of America", flag = Res.drawable.united_states),
    UZS(country = "Uzbekistan", flag = Res.drawable.uzbekista_n),
    VES(country = "Venezuela", flag = Res.drawable.venezuela),
    VND(country = "Vietnam", flag = Res.drawable.vietnam),
    VUV(country = "Vanuatu", flag = Res.drawable.vanuatu),
    WST(country = "Samoa", flag = Res.drawable.samoa),
    XAF(country = "Central African Republic", flag = Res.drawable.central_african_republic),
    YER(country = "Yemen", flag = Res.drawable.yemen),
    ZAR(country = "South Africa", flag = Res.drawable.south_africa),
    ZMW(country = "Zambia", flag = Res.drawable.zambia),
    ZWL(country = "Zimbabwe", flag = Res.drawable.zimbabwe)
}