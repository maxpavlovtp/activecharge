import React, { useEffect, useRef, useState } from "react";
import {
  Link,
  Navigate,
  Outlet,
  useLocation,
  useSearchParams,
} from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import "./layout.css";
import logo from "../../assets/logo.png";
import { useTranslation } from "react-i18next";
import MainImgLoadingLazy from "../lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../assets/logoTiny.png";
import { Nav, Navbar } from "react-bootstrap";

export default function Layout() {
  const [routeTo, setRouteTo] = useState<any>("/main");
  const [open, setOpen] = useState<any>(null);
  const [expanded, setExpanded] = useState<any>(null);

  const [searchParams] = useSearchParams();
  let stationNumbers: any = searchParams.get("station");
  const { deviceStatus, isGotDeviceStatus } = useAppSelector(
    (state) => state.fetchReducer
  );

  useEffect(() => {
    deviceStatus?.state === "IN_PROGRESS"
      ? setRouteTo(`/charging?station=${stationNumbers}`)
      : setRouteTo(`/`);
  }, [isGotDeviceStatus]);

  const closeMenu = () => {
    setOpen(!open);
    console.log(open);
  };
  let toggleStatus = !open ? "toggle-icon" : "toggle-icon open";

  const lngs: any = {
    ua: { nativeName: "Укр" },
    en: { nativeName: "Eng" },
  };

  const { t, i18n } = useTranslation();

  return (
    <div className="generalContainer">
      <Navbar className="stickyNav" bg="light" expand="lg" collapseOnSelect>
        <Link
          style={{ textDecoration: "none" }}
          reloadDocument={true}
          className="homeLink"
          to={routeTo}
        >
          <div className="logoContainer">
            <MainImgLoadingLazy
              src={logo}
              alt={"logo"}
              placeholderSrc={placehoderSrc}
              width="40"
              heigth="40"
            />
          </div>
          <h3 className="logoText">220-km.com</h3>
        </Link>
        <Navbar.Toggle onClick={closeMenu} bsPrefix={`${toggleStatus}`} />
        <Navbar.Collapse>
          <Nav className="ml-auto mt-auto navs">
            <Nav.Item>
              <Nav.Link
                style={{ textDecoration: "none" }}
                className="links"
                onClick={closeMenu}
                eventKey="1"
                as={Link}
                to={
                  stationNumbers === null ? "/" : `/?station=${stationNumbers}`
                }
              >
                {t("landingLink")}
              </Nav.Link>
            </Nav.Item>

            {stationNumbers !== "null" && stationNumbers !== null && (
              <Nav.Item>
                <Nav.Link
                  style={{ textDecoration: "none" }}
                  className="links"
                  onClick={closeMenu}
                  eventKey="2"
                  as={Link}
                  to={`/start?station=${stationNumbers}`}
                >
                  {t("chargeLink")}
                </Nav.Link>
              </Nav.Item>
            )}

            <Nav.Item>
              <Nav.Link
                style={{ textDecoration: "none" }}
                className="links"
                onClick={closeMenu}
                eventKey="3"
                as={Link}
                to={`/contacts?station=${stationNumbers}`}
              >
                {t("contacts")}
              </Nav.Link>
            </Nav.Item>

            <Nav.Item>
              <Nav.Link
                style={{ textDecoration: "none" }}
                className="links"
                onClick={closeMenu}
                eventKey="4"
                as={Link}
                to={`/contract?station=${stationNumbers}`}
              >
                {t("offer")}
              </Nav.Link>
            </Nav.Item>

            <Nav.Item>
              <Nav.Link eventKey="5" onClick={closeMenu}>
                <div className="langContainer">
                  {Object.keys(lngs).map((lng: any) => (
                    <button
                      className="btnLang"
                      key={lng}
                      style={{
                        fontWeight:
                          i18n.resolvedLanguage === lng ? "bold" : "normal",
                      }}
                      type="submit"
                      onClick={() => i18n.changeLanguage(lng)}
                    >
                      {lngs[lng].nativeName}
                    </button>
                  ))}
                </div>
              </Nav.Link>
            </Nav.Item>
          </Nav>
        </Navbar.Collapse>
      </Navbar>

      <Outlet />

      <footer className="footerBox">
        <p className="footerText">
          ©2022 {t("footer.part1")}. {t("footer.part2")}{" "}
          <a
            className="footerLink"
            href="https://www.facebook.com/zeBoosterLab/"
          >
            ZE Booster Lab
          </a>{" "}
          {t("footer.part3")}
        </p>
      </footer>
    </div>
  );
}
