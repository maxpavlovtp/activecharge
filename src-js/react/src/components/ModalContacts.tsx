import React from "react";
import { useTranslation } from "react-i18next";
import Emoji from "./emoji/Emoji";
import Modal from "./modal/Modal";
import "../App/contactsScreen/ContactsSection.css";

export default function ModalContacts({
  modalActive,
  setModalActive,
  MAX_PHONE_NUM,
  DIMA_PHONE_NUM,
}: {
  modalActive: any;
  setModalActive: any;
  MAX_PHONE_NUM: any;
  DIMA_PHONE_NUM: any;
}) {
  const { t } = useTranslation();

  return (
    <Modal active={modalActive} setActive={setModalActive}>
      <div className="telephoneContainer">
        <p className="tapForCall">{t("tapCall")}:</p>
        <div className="numberCont">
          <Emoji symbol="📲" label="phone" />
          <a href={`tel:${MAX_PHONE_NUM[0]}`} className="telephone">
            {t("telMax")}: {MAX_PHONE_NUM[1]}
          </a>
        </div>
        <div className="numberCont">
          <Emoji symbol="📲" label="phone" />
          <a href={`tel:${DIMA_PHONE_NUM[0]}`} className="telephone">
            {t("telDima")}: {DIMA_PHONE_NUM[1]}
          </a>
        </div>
      </div>
    </Modal>
  );
}
