import React, { useState } from "react";
import "./ContactsSection.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faInstagram,
  faTelegram,
  faWhatsapp,
} from "@fortawesome/free-brands-svg-icons";
import { faPhoneSquareAlt } from "@fortawesome/free-solid-svg-icons";
import { useTranslation } from "react-i18next";
import Modal from "../../../components/modal/Modal";

const MainSection: React.FC = () => {
  const [modalActive, setModalActive] = useState(false);
  const { t } = useTranslation();

  return (
    <div className="contactsBox">
      <div className="contactsCont">
        <div className="firstPart">
          <div className="viber social" onClick={() => setModalActive(true)}>
            <FontAwesomeIcon icon={faPhoneSquareAlt} size="4x" />
            <p className="linkName">{t("callUs")}</p>
          </div>

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
          <a
            href="https://www.instagram.com/_220_km.com_/"
            className="instagram social"
          >
            <FontAwesomeIcon icon={faInstagram} size="4x" />
            <p className="linkName">{t("instagram")}</p>
          </a>
          <Modal active={modalActive} setActive={setModalActive}>
            <div className="telephoneContainer">
              <a href="tel:+380933235022" className="telephone">
                {t("telMax")}: 093-323-50-22
              </a>
              <a href="tel:+380978379316" className="telephone">
                {t("telDima")}: 097-837-93-16
              </a>
            </div>
          </Modal>
        </div>
      </div>
    </div>
  );
};

export default MainSection;
