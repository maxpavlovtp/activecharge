import React, { useState } from "react";
import styles from "./Header.module.css";
import logo from "../../assets/logo.png";
import { Link } from "react-router-dom";
import Navigation from "./Navigation";
import MobileNavigation from "./MobileNavigation";
import { useAppSelector } from "../../hooks/reduxHooks";

const Header = ({ linkTo }: { linkTo: string }) => {
  const [fix, setFix] = useState(false);

  const { isDeviceOn } = useAppSelector((state) => state.fetchReducer);

  const fixed = () => {
    if (window.scrollY >= 20) {
      setFix(true);
    } else {
      setFix(false);
    }
  };

  window.addEventListener("scroll", fixed);
  return (
    <header className={styles.headerBox}>
      <nav className={fix ? styles.paddingBoxFixed : styles.paddingBox}>
        <Link className={styles.homeLink} to={linkTo}>
          <div className={styles.logoContainer}>
            <img className={styles.logoImg} src={logo} alt="logo" />
          </div>
          <h3 className={styles.logoText}>220-km.com</h3>
        </Link>

        <Navigation />
        <MobileNavigation />
      </nav>
    </header>
  );
};

export default Header;
