package pfe.project.back.Repo;

import pfe.project.back.Dto.Mail;

public interface EmailService {
    public void sendCodeByMail(Mail mail);
}
