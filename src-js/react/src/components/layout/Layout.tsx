import { useEffect, useState } from "react";
import { Link, Outlet, useSearchParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import "./layout.css";
import logo from "../../assets/logo.png";
import whiteLogo from "../../assets/whiteLogoNav.png";
import lightMode from "../../assets/lightMode.png";
import nightMode from "../../assets/nightMode.png";
import mainImg from "../../assets/charging.png";
import whiteMainImg from "../../assets/whiteCharging.png";
import { useTranslation } from "react-i18next";
import MainImgLoadingLazy from "../lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../assets/logoTiny.png";
import { Container, Nav, Navbar } from "react-bootstrap";
import { ThemeProvider } from "styled-components";
import {
  getStationInfo,
  getUiNightMode,
} from "../../store/reducers/ActionCreators";
import {
  FooterLink,
  GlobalStyles,
  LinksColor,
  NavBar,
  NavLink,
  Translate,
} from "../globalStyles";
import { lightTheme, darkTheme } from "../darkTheme/Theme";
import { useLocalStorage } from "../../hooks/useLocalStorage";
import { deviceOnlineStatus } from "../../store/reducers/FetchSlice";

export default function Layout() {
  const [routeTo, setRouteTo] = useState<any>("/start");
  const [open, setOpen] = useState<any>(null);

  const [searchParams] = useSearchParams();
  let stationNumbers: any = searchParams.get("station");
  const { deviceStatus, isGotDeviceStatus, uiNightMode } = useAppSelector(
    (state) => state.fetchReducer
  );
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (deviceStatus?.lastJob?.state === "IN_PROGRESS") {
      setRouteTo(`/charging?station=${stationNumbers}`);
    } else if (
      deviceStatus?.lastJob?.state === "DONE" ||
      deviceStatus?.lastJobPresented === false
    ) {
      setRouteTo(`/start?station=${stationNumbers}`);
    } else if (stationNumbers === null) {
      setRouteTo("/");
    }
  }, [isGotDeviceStatus]);

  const closeMenu = () => {
    setOpen(!open);
    console.log(open);
  };

  const lngs: any = {
    ua: { nativeName: "Укр" },
    en: { nativeName: "Eng" },
  };

  const { t, i18n } = useTranslation();

  const [togglerStatus, setTogglerStatus] = useLocalStorage<boolean>(
    "themeTogglerStatus",
    false
  );

  const [theme, setTheme] = useLocalStorage<string>("themeMode", "light");
  const [logoTheme, setLogoTheme] = useLocalStorage<string>("logoImg", logo);
  const [modeImg, setModeImg] = useLocalStorage<string>("btnMode", nightMode);
  const [mainImgTheme, setMainImgTheme] = useLocalStorage<string>(
    "mainImg",
    mainImg
  );

  const darkModeSetter = () => {
    setTheme("dark");
    setLogoTheme(whiteLogo);
    setModeImg(lightMode);
    setMainImgTheme(whiteMainImg);
  };
  const lightModeSetter = () => {
    setTheme("light");
    setLogoTheme(logo);
    setMainImgTheme(mainImg);
    setModeImg(nightMode);
  };

  useEffect(() => {
    if (togglerStatus === false) {
      dispatch(getUiNightMode(stationNumbers));
      console.log(deviceStatus);
      if (uiNightMode === false) {
        lightModeSetter();
      } else if (uiNightMode === true) {
        darkModeSetter();
      }
    }
  }, [uiNightMode]);

  const themeToggler = () => {
    setTogglerStatus(true);
    if (theme === "light") {
      darkModeSetter();
    } else {
      lightModeSetter();
    }
  };
  let toggleStatus = !open ? "toggle-icon" : "open toggle-icon ";

  return (
    <ThemeProvider theme={theme === "light" ? lightTheme : darkTheme}>
      <>
        <GlobalStyles />
        <Container
          fluid
          style={{ height: "100vh", display: "flex" }}
          className="flex-column justify-content-between ml-0 pl-0 mr-0 pr-0"
        >
          <NavBar
            className="justify-content-between align-items-center shadow-sm"
            expand="lg"
            collapseOnSelect
            // ref={domNode}
          >
            <LinksColor
              reloadDocument
              onClick={() => dispatch(deviceOnlineStatus(null))}
              to={routeTo}
              className="flex-row align-items-center"
            >
              <div className="logoContainer">
                <MainImgLoadingLazy
                  src={logoTheme}
                  alt={"logo"}
                  placeholderSrc={placehoderSrc}
                  width="40"
                  heigth="40"
                />
              </div>

              <h3 className="ml-1 mb-0 logoText">220-km.com</h3>
            </LinksColor>
            <Navbar.Toggle onClick={closeMenu} bsPrefix={`${toggleStatus}`} />
            <Navbar.Collapse>
              <Nav className="ml-auto mt-auto align-items-center">
                <Nav.Item>
                  <Nav.Link
                    style={{ textDecoration: "none" }}
                    className="nav-link links"
                    onClick={() => {
                      closeMenu();
                      dispatch(deviceOnlineStatus(null));
                    }}
                    eventKey="1"
                    as={Link}
                    to={
                      stationNumbers === null
                        ? "/"
                        : `/?station=${stationNumbers}`
                    }
                  >
                    <NavLink>{t("landingLink")}</NavLink>
                  </Nav.Link>
                </Nav.Item>

                {stationNumbers !== "null" && stationNumbers !== null && (
                  <Nav.Item>
                    <Nav.Link
                      style={{ textDecoration: "none" }}
                      className="nav-link links"
                      onClick={closeMenu}
                      eventKey="2"
                      as={Link}
                      to={
                        deviceStatus?.lastJob?.state === "IN_PROGRESS"
                          ? `/charging?station=${stationNumbers}`
                          : `/start?station=${stationNumbers}`
                      }
                    >
                      <NavLink>{t("chargeLink")}</NavLink>
                    </Nav.Link>
                  </Nav.Item>
                )}

                <Nav.Item>
                  <Nav.Link
                    // style={{ textDecoration: "none" }}
                    className="nav-link links"
                    onClick={closeMenu}
                    eventKey="3"
                    as={Link}
                    to={`/contacts?station=${stationNumbers}`}
                  >
                    <NavLink>{t("contacts")}</NavLink>
                  </Nav.Link>
                </Nav.Item>

                <Nav.Item>
                  <Nav.Link
                    style={{ textDecoration: "none" }}
                    className="nav-link links"
                    onClick={closeMenu}
                    eventKey="4"
                    as={Link}
                    to={`/contract?station=${stationNumbers}`}
                  >
                    <NavLink>{t("offer")}</NavLink>
                  </Nav.Link>
                </Nav.Item>

                <Nav.Item className="ml-3 mr-3">
                  <Nav.Link
                    className="text-decoration-none"
                    eventKey="5"
                    onClick={closeMenu}
                  >
                    {Object.keys(lngs).map((lng: any) => (
                      <Translate
                        className="ml-1 btnLang"
                        key={lng}
                        style={{
                          fontWeight:
                            i18n.resolvedLanguage === lng ? "bold" : "normal",
                        }}
                        type="submit"
                        onClick={() => i18n.changeLanguage(lng)}
                      >
                        {lngs[lng].nativeName}
                      </Translate>
                    ))}
                  </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link eventKey="6" onClick={closeMenu}>
                    <button
                      style={{ backgroundColor: "transparent", border: "none" }}
                      onClick={themeToggler}
                    >
                      <img
                        style={{ width: "30px", height: "30px" }}
                        src={modeImg}
                        alt={"theme"}
                      />
                    </button>
                  </Nav.Link>
                </Nav.Item>
              </Nav>
            </Navbar.Collapse>
          </NavBar>

          <Outlet context={[mainImgTheme, setMainImgTheme]} />

          <footer className="footerBox">
            <p className="footerText">
              ©2022 {t("footer.part1")}. {t("footer.part2")}{" "}
              <FooterLink
                className="footerLink"
                href="https://www.facebook.com/zeBoosterLab/"
              >
                ZE Booster Lab
              </FooterLink>{" "}
              {t("footer.part3")}
            </p>
          </footer>
        </Container>
      </>
    </ThemeProvider>
  );
}
