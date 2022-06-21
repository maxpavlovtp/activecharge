import React from "react";
import "./ContactsSection.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faInstagram,
  faTelegram,
  faWhatsapp,
} from "@fortawesome/free-brands-svg-icons";
import { faPhoneSquareAlt } from "@fortawesome/free-solid-svg-icons";
import { useTranslation } from "react-i18next";

const MainSection: React.FC = () => {
  const { t } = useTranslation();

  return (
    <div className="contactsBox">
      <div className="contactsCont">
        <div className="firstPart">
          <a
            href="https://www.instagram.com/_220_km.com_/"
            className="instagram social"
          >
            <FontAwesomeIcon icon={faInstagram} size="4x" />
            <p className="linkName">{t("instagram")}</p>
          </a>
          <a href="https://telegram.me/maxpavlovdp" className="telegram social">
            <FontAwesomeIcon icon={faTelegram} size="4x" />
            <p className="linkName">{t("telegram")}</p>
          </a>
        </div>
        <div className="secondPart">
          <a href="https://wa.me/+380971983759" className="watsapp social">
            <FontAwesomeIcon icon={faWhatsapp} size="4x" />
            <p className="linkName">{t("watsapp")}</p>
          </a>
          <a href="tel:+380984109626" className="viber social">
            <FontAwesomeIcon icon={faPhoneSquareAlt} size="4x" />
            <p className="linkName">{t("callUs")}</p>
          </a>
        </div>
      </div>
    </div>
  );
};

export default MainSection;
