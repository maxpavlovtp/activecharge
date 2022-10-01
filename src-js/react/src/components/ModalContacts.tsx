import React from "react";
import { useTranslation } from "react-i18next";
import Emoji from "./emoji/Emoji";
import Modal from "./modal/Modal";
import "../App/contactsScreen/ContactsSection.css";

export default function ModalContacts({
  modalActive,
  setModalActive,
}: {
  modalActive: any;
  setModalActive: any;
}) {
  const { t } = useTranslation();

  return (
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
  );
}
