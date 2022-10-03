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
import { Col, Container, Row } from "react-bootstrap";
import ModalContacts from "../../components/modal/ModalContacts";

export const MAX_PHONE_NUM = [+380971983759, "097-198-37-59"];
export const DIMA_PHONE_NUM = [+380978379316, "097-837-93-16"];

const MainSection: React.FC = () => {
  const [modalActive, setModalActive] = useState(false);
  const { t } = useTranslation();

  return (
    <Container>
      <Row className="justify-content-center mb-5">
        <Col
          as={"div"}
          className="text-center viber"
          onClick={() => {
            setModalActive(true);
            console.log(modalActive);
          }}
        >
          <FontAwesomeIcon icon={faPhoneSquareAlt} size="4x" />
          <p className="linkName">{t("callUs")}</p>
        </Col>

        <Col
          as={"a"}
          href="https://telegram.me/maxpavlovdp"
          className="text-center telegram"
        >
          <FontAwesomeIcon icon={faTelegram} size="4x" />
          <p className="linkName">{t("telegram")}</p>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col
          as={"a"}
          href={`https://wa.me/${MAX_PHONE_NUM[0]}`}
          className="text-center watsapp"
        >
          <FontAwesomeIcon icon={faWhatsapp} size="4x" />
          <p className="linkName">{t("watsapp")}</p>
        </Col>

        <Col
          as={"a"}
          href="https://www.instagram.com/_220_km.com_/"
          className="text-center instagram"
        >
          <FontAwesomeIcon icon={faInstagram} size="4x" />
          <p className="linkName">{t("instagram")}</p>
        </Col>

        <ModalContacts
          modalActive={modalActive}
          setModalActive={setModalActive}
          MAX_PHONE_NUM={MAX_PHONE_NUM}
          DIMA_PHONE_NUM={DIMA_PHONE_NUM}
        />
      </Row>
    </Container>
  );
};

export default MainSection;
