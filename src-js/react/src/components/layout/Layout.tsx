import React, { useEffect, useState } from "react";
import {
  Link,
  Navigate,
  Outlet,
  useLocation,
  useSearchParams,
} from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import styles from "./Header.module.css";
import logo from "../../assets/logo.png";
import Navigation from "../header/Navigation";
import MobileNavigation from "../header/MobileNavigation";
import { useTranslation } from "react-i18next";
import MainImgLoadingLazy from "../lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../assets/logoTiny.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Home } from "@material-ui/icons";
import { faHome } from "@fortawesome/free-solid-svg-icons";

export default function Layout() {
  const [fix, setFix] = useState(false);
  const [routeTo, setRouteTo] = useState<any>("/main");

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

  const fixed = () => {
    if (window.scrollY >= 20) {
      setFix(true);
    } else {
      setFix(false);
    }
  };

  const location = useLocation();

  window.addEventListener("scroll", fixed);
  const { t } = useTranslation();
  return (
    <div className={styles.generalContainer}>
      <header className={styles.headerBox}>
        <nav className={fix ? styles.paddingBoxFixed : styles.paddingBox}>
          <Link reloadDocument={true} className={styles.homeLink} to={routeTo}>
            <div className={styles.logoContainer}>
              <MainImgLoadingLazy
                src={logo}
                alt={"logo"}
                placeholderSrc={placehoderSrc}
                width="40"
                heigth="40"
              />
            </div>
            <h3 className={styles.logoText}>220-km.com</h3>
          </Link>

          <Navigation stationNumbers={stationNumbers} />
          <MobileNavigation stationNumbers={stationNumbers} />
        </nav>
      </header>

      <Outlet />

      {location.pathname !== "/" && (
        <Link className={styles.backToHome} to="/">
          <FontAwesomeIcon icon={faHome} size="2x" />
        </Link>
      )}

      <footer className={styles.footerBox}>
        <p className={styles.footerText}>
          ©2022 {t("footer.part1")}. {t("footer.part2")}{" "}
          <a
            className={styles.footerLink}
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
