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
import Modal from "../../components/modal/Modal";
import Emoji from "../../components/emoji/Emoji";
import { Col, Container, Row } from "react-bootstrap";

const MainSection: React.FC = () => {
  const [modalActive, setModalActive] = useState(false);
  const { t } = useTranslation();

  return (
    <Container>
      <Row className="justify-content-center mb-5">
        <Col as={"div"} className="text-center viber" onClick={() => {
          setModalActive(true)
          console.log(modalActive)}}>
          <FontAwesomeIcon icon={faPhoneSquareAlt} size="4x" />
          <p className="linkName">{t("callUs")}</p>
        </Col>

        <Col as={"a"} href="https://telegram.me/maxpavlovdp" className="text-center telegram">
          <FontAwesomeIcon icon={faTelegram} size="4x" />
          <p className="linkName">{t("telegram")}</p>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col as={"a"} href="https://wa.me/+380971983759" className="text-center watsapp">
          <FontAwesomeIcon icon={faWhatsapp} size="4x" />
          <p className="linkName">{t("watsapp")}</p>
        </Col>

        <Col as={"a"}
          href="https://www.instagram.com/_220_km.com_/"
          className="text-center instagram"
        >
          <FontAwesomeIcon icon={faInstagram} size="4x" />
          <p className="linkName">{t("instagram")}</p>
        </Col>

        <Modal active={modalActive} setActive={setModalActive}>
          <div className="telephoneContainer">
            <p className="tapForCall">{t("tapCall")}:</p>
            <div className="numberCont">
              <Emoji symbol="📲" label="phone" />
              <a href="tel:+380971983759" className="telephone">
                {t("telMax")}: 097-198-37-59
              </a>
            </div>
            <div className="numberCont">
              <Emoji symbol="📲" label="phone" />
              <a href="tel:+380978379316" className="telephone">
                {t("telDima")}: 097-837-93-16
              </a>
            </div>
          </div>
        </Modal>
      </Row>
    </Container>
  );
};

export default MainSection;
