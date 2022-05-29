import React from "react";
import "./ContactsSection.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faInstagram,
  faTelegram,
  faWhatsapp,
  faViber,
} from "@fortawesome/free-brands-svg-icons";

const MainSection: React.FC = () => {
  return (
    <div className="contactsBox">
      <div className="contactsCont">
        <div className="firstPart">
          <a
            href="https://www.instagram.com/_220_km.com_/"
            className="instagram social"
          >
            <FontAwesomeIcon icon={faInstagram} size="4x" />
            <p className="linkName">Instagram</p>
          </a>
          <a href="https://telegram.me/maxpavlovdp" className="telegram social">
            <FontAwesomeIcon icon={faTelegram} size="4x" />
            <p className="linkName">Telegram</p>
          </a>
        </div>
        <div className="secondPart">
          <a href="https://wa.me/+380971983759" className="watsapp social">
            <FontAwesomeIcon icon={faWhatsapp} size="4x" />
            <p className="linkName">WatsApp</p>
          </a>
          <a href="viber://chat?number=+380971983759" className="viber social">
            <FontAwesomeIcon icon={faViber} size="4x" />
            <p className="linkName">Viber</p>
          </a>
        </div>
      </div>
    </div>
  );
};

export default MainSection;
